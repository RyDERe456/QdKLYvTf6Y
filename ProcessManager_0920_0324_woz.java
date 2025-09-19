// 代码生成时间: 2025-09-20 03:24:30
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ProcessManager {

    private final Runtime runtime;

    public ProcessManager(Runtime runtime) {
        this.runtime = runtime;
    }

    /**
     * 启动进程的方法
     * @param command 要执行的命令
     * @return 进程输出的结果
     */
    public String startProcess(String command) {
        try {
            Process process = runtime.exec(command);
            return readProcessOutput(process);
        } catch (IOException e) {
            throw new RuntimeException(