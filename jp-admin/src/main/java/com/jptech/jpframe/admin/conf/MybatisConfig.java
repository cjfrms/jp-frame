package com.jptech.jpframe.admin.conf;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.jptech.jpframe.admin.comm.DynamicRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class MybatisConfig {

    private static final String POINTCUT_EXP = "execution(* *..service.*Service.*(..))";
    private static final String[] TRANSACTION_REQUIRED_METHOD_RULE = {
            "insert*" ,
            "create*" ,
            "save*" ,
            "add*" ,
            "update*" ,
            "delete*" ,
            "remove*" ,
            "execute*"
    };
    private static final String[] TRANSACTION_NOT_SUPPORTED_METHOD_RULE = {
            "select*" ,
            "get*" ,
            "find*" ,
            "list*" ,
    };


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/jptech/jpframe/admin/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDefaultTargetDataSource(druidDataSource());
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds-master", druidDataSource());
        dataSourceMap.put("ds-slave", slave());
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean
    public TransactionInterceptor txAdvice(PlatformTransactionManager platformTransactionManager) {
        NameMatchTransactionAttributeSource attrSource = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute requiredTx = this.requiredTransactionRule();
        RuleBasedTransactionAttribute readOnly = this.readOnlyTransactionRule();

        for(String attr : TRANSACTION_REQUIRED_METHOD_RULE){
            attrSource.addTransactionalMethod(attr,requiredTx);
        }
        for(String attr : TRANSACTION_NOT_SUPPORTED_METHOD_RULE){
            attrSource.addTransactionalMethod(attr,readOnly);
        }
        TransactionInterceptor txAdvice = new TransactionInterceptor(platformTransactionManager, attrSource);
        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor(PlatformTransactionManager platformTransactionManager) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(POINTCUT_EXP);
        DefaultPointcutAdvisor xx = new DefaultPointcutAdvisor(pointcut, txAdvice(platformTransactionManager));
        return new DefaultPointcutAdvisor(pointcut, txAdvice(platformTransactionManager));
    }

    private RuleBasedTransactionAttribute requiredTransactionRule () {
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        required.setRollbackRules( Collections.singletonList( new RollbackRuleAttribute( Exception.class ) ) );
        required.setPropagationBehavior( TransactionDefinition.PROPAGATION_REQUIRED );
        required.setTimeout( TransactionDefinition.TIMEOUT_DEFAULT );
        return required;
    }


    private RuleBasedTransactionAttribute readOnlyTransactionRule () {
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly( true );
        readOnly.setPropagationBehavior( TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        return readOnly;
    }


}
