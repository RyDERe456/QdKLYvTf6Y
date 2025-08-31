// 代码生成时间: 2025-09-01 06:18:30
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

// 哈希值计算工具类
# 扩展功能模块
@Service
public class HashService {
# 改进用户体验

    private static final String HASH_ALGORITHM = "SHA-256";
    private MessageDigest messageDigest;

    // 初始化MessageDigest对象
    @PostConstruct
    public void init() {
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // 处理算法不支持的情况
            throw new RuntimeException("Hash algorithm not supported: " + HASH_ALGORITHM, e);
        }
    }

    // 计算字符串的哈希值
    public String computeHash(String input) {
        if (StringUtils.isEmpty(input)) {
# NOTE: 重要实现细节
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        messageDigest.update(input.getBytes());
        byte[] digest = messageDigest.digest();
        return bytesToHex(digest);
    }

    // 将字节数组转换为十六进制字符串
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
# 扩展功能模块
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
