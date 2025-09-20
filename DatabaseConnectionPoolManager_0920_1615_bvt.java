// 代码生成时间: 2025-09-20 16:15:31
package com.example.demo.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import javax.persistence.EntityManagerFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
# 扩展功能模块
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatabaseConnectionPoolManager {

    // 这里使用了@Value注解来注入配置文件中的值
    @Value("\${spring.datasource.url}")
    private String dbUrl;

    @Value("\${spring.datasource.username}")
    private String username;

    @Value("\${spring.datasource.password}")
# 添加错误处理
    private String password;

    @Value("\${spring.datasource.driver-class-name}")
# 改进用户体验
    private String driverClassName;

    // 使用@ConfigurationProperties注解来自动绑定配置文件中的属性
# NOTE: 重要实现细节
    @ConfigurationProperties(prefix = "spring.datasource")
    private DataSourceProperties dataSourceProperties;

    @Bean
    @Primary
    public DataSource dataSource() {
        // 使用DataSourceBuilder来创建DataSource
        DataSource dataSource = DataSourceBuilder.create()
                .url(dbUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .type(BasicDataSource.class)
# NOTE: 重要实现细节
                .build();
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource, JpaProperties jpaData) {
        // 创建EntityManagerFactory
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties(dataSource, jpaData))
                .packages("com.example.demo.model") // 指定实体类所在的包
                .persistenceUnit("default")
# NOTE: 重要实现细节
                .build();
    }
# TODO: 优化性能

    @Bean
# TODO: 优化性能
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        // 创建事务管理器
        return new DataSourceTransactionManager(dataSource);
    }

    // 获取JPA相关的属性
    private Map<String, String> getVendorProperties(DataSource dataSource, JpaProperties jpaData) {
# TODO: 优化性能
        return jpaData.getHibernateProperties(dataSource);
    }

    // 错误处理可以在这里实现，例如数据库连接失败时的处理逻辑
    // 这里可以根据需要添加更多的错误处理逻辑
}