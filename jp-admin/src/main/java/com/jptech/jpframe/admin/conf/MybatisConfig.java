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
public class MybatisConfig {

    private static final String[] DEFAULT_TRANSACTION_BEAN_NAMES = { "*Service" , "*ServiceImpl" };

    private static final String[] DEFAULT_REQUIRED_METHOD_RULE_TRANSACTION_ATTRIBUTES  = {
            "add*" ,
            "save*" ,
            "insert*" ,
            "delete*" ,
            "update*" ,
            "edit*" ,
            "batch*" ,
            "create*" ,
            "remove*" ,
    };

    private static final String[] DEFAULT_READ_ONLY_METHOD_RULE_TRANSACTION_ATTRIBUTES = {
            "get*" ,
            "count*" ,
            "find*" ,
            "query*" ,
            "select*" ,
            "list*" ,
            "*" ,
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

/*    @Bean
    public DataSourceTransactionManager transactionManager() throws IOException {
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager(dynamicDataSource());
        return transactionManager;
    }*/

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
    public TransactionInterceptor txAdvice(PlatformTransactionManager platformTransactionManager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
         /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT );
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap( txMap );
        TransactionInterceptor txAdvice = new TransactionInterceptor(platformTransactionManager, source);
        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor(PlatformTransactionManager platformTransactionManager) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* *..service.*Service.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice(platformTransactionManager));
    }


    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
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
