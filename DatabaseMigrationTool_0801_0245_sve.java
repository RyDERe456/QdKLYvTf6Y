// 代码生成时间: 2025-08-01 02:45:11
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.io.IOException;

@Component
public class DatabaseMigrationTool {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationTool.class);

    @Autowired
# NOTE: 重要实现细节
    private DriverManagerDataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    /**
# 扩展功能模块
     * Migrate the database by running the SQL scripts located in 'classpath:db/migration/'
# FIXME: 处理边界情况
     */
    public void migrateDatabase() {
        try {
            Resource[] scripts = {new ClassPathResource("db/migration/schema.sql"),
                                new ClassPathResource("db/migration/data.sql")};
            DatabasePopulatorUtils.executeSqlScript(dataSource, scripts);
            logger.info("Database migration completed successfully.");
        } catch (IOException | SQLException e) {
            logger.error("Error occurred during database migration: " + e.getMessage(), e);
# TODO: 优化性能
            throw new RuntimeException("Database migration failed", e);
# 扩展功能模块
        }
    }
}
