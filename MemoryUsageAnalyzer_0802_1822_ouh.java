// 代码生成时间: 2025-08-02 18:22:59
import org.springframework.stereotype.Component;
import org.springframework.boot.info.BuildProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import javax.annotation.PostConstruct;

/**
 * 组件用于分析内存使用情况
 * 使用了Spring Boot的组件注解和服务注解。
 * 同时，使用了@ConditionalOnProperty注解来控制是否启用该组件。
 */
@Component
@ConditionalOnProperty(name = "memory.analyzer.enabled", havingValue = "true")
public class MemoryUsageAnalyzer {

    private final BuildProperties buildProperties;

    // 使用构造函数注入BuildProperties
    public MemoryUsageAnalyzer(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    // 在组件创建后立即执行的方法
    @PostConstruct
    public void init() {
        // 输出构建信息用于调试
        System.out.println("Build name: " + buildProperties.getName());
        System.out.println("Build artifact: " + buildProperties.getArtifact());
    }

    /**
     * 获取内存使用情况
     * 
     * @return 内存使用详情
     */
    public String getMemoryUsage() {
        try {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            return "Heap Memory Usage: 
" +
                    "  Used: " + heapMemoryUsage.getUsed() + " 
" +
                    "  Committed: " + heapMemoryUsage.getCommitted() + " 
" +
                    "  Max: " + heapMemoryUsage.getMax() + " 
" +
                    "  Used %: " + (heapMemoryUsage.getUsed() / (double)heapMemoryUsage.getMax()) * 100 + "% 
" +
                    "Non-Heap Memory Usage: 
" +
                    "  Used: " + nonHeapMemoryUsage.getUsed() + " 
" +
                    "  Committed: " + nonHeapMemoryUsage.getCommitted() + " 
" +
                    "  Max: " + nonHeapMemoryUsage.getMax() + " 
" +
                    "  Used %: " + (nonHeapMemoryUsage.getUsed() / (double)nonHeapMemoryUsage.getMax()) * 100 + "%";
        } catch (Exception e) {
            // 错误处理
            return "Error retrieving memory usage: " + e.getMessage();
        }
    }
}
