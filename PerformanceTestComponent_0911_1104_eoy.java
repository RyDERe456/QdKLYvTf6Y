// 代码生成时间: 2025-09-11 11:04:28
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PerformanceTestComponent {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestComponent.class);
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final int threadCount;
    private final long duration;
    private final TimeUnit timeUnit;

    public PerformanceTestComponent(@Value("${performance.test.threadCount:10}") int threadCount,
                                  @Value("${performance.test.duration:10}") long duration,
                                  @Value("${performance.test.timeUnit:SECONDS}") TimeUnit timeUnit) {
        this.restTemplate = new RestTemplate();
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.threadCount = threadCount;
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    @GetMapping("/performanceTest")
    public String performPerformanceTest() {
        try {
            Runnable worker = () -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/test", String.class);
                    if (!response.getStatusCode().is2xxSuccessful()) {
                        throw new RuntimeException("Failed to get successful response");
                    }
                } catch (Exception e) {
                    logger.error("Error during performance test", e);
                }
            };
            for (int i = 0; i < threadCount; i++) {
                executorService.submit(worker);
            }
            executorService.shutdown();
            executorService.awaitTermination(duration, timeUnit);
            logger.info("Performance test completed successfully");
            return "Performance test completed";
        } catch (InterruptedException e) {
            logger.error("Performance test was interrupted", e);
            Thread.currentThread().interrupt();
            return "Performance test was interrupted";
        }
    }
}