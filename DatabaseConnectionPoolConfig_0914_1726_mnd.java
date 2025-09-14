// 代码生成时间: 2025-09-14 17:26:25
package com.example.demo.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;

/**
 * Configuration class for database connection pool.
 */
@Configuration
public class DatabaseConnectionPoolConfig {

    /**
     * DataSource bean for development profile.
     * This uses Apache Commons DBCP2 as the connection pool.
     */
    @Bean
    @Profile("dev")
    public DataSource dataSourceForDev() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/devdb");
        dataSource.setUsername("devuser");
        dataSource.setPassword("devpass");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Set connection pool properties
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(2);
        // Error handling for the connection pool
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(5000);
        return dataSource;
    }

    /**
     * DataSource bean for production profile.
     * This uses Apache Commons DBCP2 as the connection pool.
     */
    @Bean
    @Profile("prod")
    public DataSource dataSourceForProd() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://prod-db:3306/proddb");
        dataSource.setUsername("produser");
        dataSource.setPassword("prodpass");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Set connection pool properties
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(2);
        // Error handling for the connection pool
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(5000);
        return dataSource;
    }
}
