// 代码生成时间: 2025-08-26 01:19:14
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextFileAnalyzer {

    private final ResourceLoader resourceLoader;

    public TextFileAnalyzer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 分析给定的文本文件
     * @param filePath 文件路径
     * @return 文件内容的分析结果
     */
    public String analyzeTextFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            if (resource.exists()) {
                return new String(Files.readAllBytes(resource.getFile().toPath()));
            } else {
                throw new IOException("文件不存在");
            }
        } catch (IOException e) {
            // 错误处理
            return "无法读取文件: " + e.getMessage();
        }
    }
}
