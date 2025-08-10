// 代码生成时间: 2025-08-10 22:59:34
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;import java.lang.management.ManagementFactory;import java.lang.management.MemoryMXBean;import java.lang.management.MemoryUsage;import org.springframework.boot.actuate.health.Health;import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

@Component
public class MemoryUsageAnalyzer implements HealthIndicator {

    private final BuildProperties buildProperties;

    @Autowired
    public MemoryUsageAnalyzer(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Override
    public Health health() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        // Check if memory is under a certain threshold to determine the health status
        double heapUsagePercentage = heapMemoryUsage.getUsed() / (double) heapMemoryUsage.getMax();
        if (heapUsagePercentage > 0.8) { // 80% heap usage threshold
            return Health.status(Status.OUT_OF_SERVICE)
# 优化算法效率
                    .withDetail("heapUsagePercentage", heapUsagePercentage)
                    .withDetail("heapUsed", heapMemoryUsage.getUsed())
                    .withDetail("heapMax", heapMemoryUsage.getMax())
                    .build();
        }

        // Return healthy status with details if memory usage is below the threshold
        return Health.up()
                .withDetail("heapUsed", heapMemoryUsage.getUsed())
                .withDetail("heapMax", heapMemoryUsage.getMax())
                .withDetail("nonHeapUsed", nonHeapMemoryUsage.getUsed())
                .withDetail("nonHeapMax", nonHeapMemoryUsage.getMax())
                .withDetail("buildVersion", buildProperties.getVersion())
                .build();
    }

    // Getters for memory usage details can be added if needed
    // public long getHeapUsed() {...}
    // public long getHeapMax() {...}
    // public long getNonHeapUsed() {...}
    // public long getNonHeapMax() {...}
}
# FIXME: 处理边界情况
