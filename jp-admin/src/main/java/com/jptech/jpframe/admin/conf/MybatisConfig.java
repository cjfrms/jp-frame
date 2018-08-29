package com.jptech.jpframe.admin.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MybatisConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws IOException {
        //这里的数据源要和配置SqlSessionFactoryBean中配置的数据源相同，事务才会生效
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager(druidDataSource());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/jptech/jpframe/admin/mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

}
