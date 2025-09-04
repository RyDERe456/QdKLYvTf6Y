// 代码生成时间: 2025-09-04 09:23:18
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

// 定义HashCalculationService组件，用于计算哈希值
@RestController
@Service
public class HashCalculationService {

    // 使用@GetMapping注解定义一个GET请求处理方法
    // 该方法接收一个名为'input'的请求参数，并返回其哈希值
    @GetMapping("/hash")
    public ResponseEntity<String> calculateHash(@RequestParam String input) {
        try {
            // 使用MessageDigest类计算指定输入的哈希值
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // 返回哈希值的响应实体
            return ResponseEntity.ok(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            // 处理哈希算法不支持的异常
            return ResponseEntity.status(500).body("Hash algorithm not supported");
        }
    }
}
