// 代码生成时间: 2025-08-31 05:38:34
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;

// 哈希值计算工具的Spring Boot组件
@Service
public class HashCalculationService {

    private MessageDigest md;

    // 在构造函数中初始化MessageDigest对象
    public HashCalculationService() {
        try {
            this.md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // 计算给定字符串的哈希值
    public String calculateHash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        md.update(input.getBytes());
        byte[] digest = md.digest();
        // 将字节数组转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

// 哈希值计算工具的REST控制器
@RestController
public class HashCalculationController {

    private final HashCalculationService hashCalculationService;

    // 通过构造器注入哈希值计算工具
    public HashCalculationController(HashCalculationService hashCalculationService) {
        this.hashCalculationService = hashCalculationService;
    }

    // 提供一个HTTP GET接口来计算哈希值
    @GetMapping("/hash")
    public ResponseEntity<String> calculateHash(@RequestParam String input) {
        try {
            String hash = hashCalculationService.calculateHash(input);
            return ResponseEntity.ok(hash);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }
}
