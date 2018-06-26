package com.ldf.redis.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by ldf on 2018/6/26.
 */
@Configuration
@EnableTransactionManagement()
public class DataSourceConfig {

    @Value("${druid.type}")
    private Class<? extends javax.sql.DataSource> dataSourceType;

    @Bean("myDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ldf")
    public DataSource myDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

}
