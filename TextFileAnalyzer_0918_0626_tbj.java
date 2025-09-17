// 代码生成时间: 2025-09-18 06:26:21
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Text file content analyzer component for Spring Boot application.
 */
@Service
public class TextFileAnalyzer {

    private final ResourceLoader resourceLoader;

    // Autowiring ResourceLoader to load resources from the classpath
    @Autowired
    public TextFileAnalyzer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Analyzes the content of a text file.
     * 
     * @param filename The name of the text file to analyze.
     * @return A string containing the contents of the text file.
     * @throws IOException If an I/O error occurs reading from the file or a malformed
     *         filename is provided.
     */
    public String analyzeTextFileContent(String filename) throws IOException {
        // Obtain the resource using the filename provided
        Resource resource = resourceLoader.getResource("classpath:/" + filename);

        if (!resource.exists()) {
            throw new IOException("The file does not exist: " + filename);
        }

        // Read the content of the file and return it as a string
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
