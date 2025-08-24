// 代码生成时间: 2025-08-24 08:11:27
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduledTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskExecutor.class);

    @Value("${scheduled.task.fixed-rate:5000}") // 每秒执行一次，可通过application.properties配置
    private long fixedRate;

    private long count = 0; // 任务执行次数计数器

    // 定义一个定时任务，按照fixedRate设定的间隔执行
    @Scheduled(fixedRate = ${fixedRate})
    public void executeTask() {
        try {
            logger.info("Scheduled task is executing. Count: " + count++);
            // 这里添加任务逻辑
        } catch (Exception e) {
            logger.error("Error occurred during scheduled task execution.", e);
        }
    }

    // 可以添加一个方法来立即执行任务，用于测试或其他需要
    public void executeTaskNow() {
        try {
            logger.info("Manual trigger of scheduled task. Count: " + count++);
            // 这里添加任务逻辑
        } catch (Exception e) {
            logger.error("Error occurred during manual trigger of scheduled task.", e);
        }
    }
}
