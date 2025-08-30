// 代码生成时间: 2025-08-30 17:29:56
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
public class ProcessManager {

    private ProcessBuilder processBuilder;

    // 初始化进程管理器
    @PostConstruct
    public void init() {
        System.out.println("ProcessManager initialized");
        // 初始化ProcessBuilder，可以在这里设置进程参数
        processBuilder = new ProcessBuilder();
    }

    // 启动进程
    public void startProcess(String... command) {
        try {
            System.out.println("Starting process... " + Arrays.toString(command));
            processBuilder.command(command);
            Process process = processBuilder.start();
            new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
                .forEach(System.out::println);
        } catch (IOException e) {
            // 错误处理
            System.err.println("Failed to start process: " + e.getMessage());
        }
    }

    // 清理资源
    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
        // 关闭资源，比如进程
        if (processBuilder != null) {
            if (processBuilder.process() != null) {
                processBuilder.process().destroy();
            }
        }
    }
}
