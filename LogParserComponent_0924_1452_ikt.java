// 代码生成时间: 2025-09-24 14:52:45
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * LogParserComponent is a Spring Boot component responsible for parsing log files.
 */
@Component
public class LogParserComponent {

    private static final Logger logger = LoggerFactory.getLogger(LogParserComponent.class);

    private final ResourceLoader resourceLoader;

    @Value("classpath:logs/*.log")
    private Resource[] logFiles;

    // Constructor injection for ResourceLoader
    public LogParserComponent(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Parses all log files and processes each line that represents an error.
     * @return A stream of error messages found in the log files.
     */
    public Stream<String> parseErrorLogs() {
        return Stream.of(logFiles)
            .flatMap(this::processLogFile)
            .filter(line -> line.contains("ERROR"));
    }

    /**
     * Reads a single log file and returns a stream of its lines.
     * @param resource The log file resource to process.
     * @return A stream of lines from the log file.
     */
    private Stream<String> processLogFile(Resource resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines();
        } catch (IOException e) {
            logger.error("Error reading log file: " + resource.getFilename(), e);
            return Stream.empty();
        }
    }
}
