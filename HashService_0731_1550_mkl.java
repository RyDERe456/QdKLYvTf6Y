// 代码生成时间: 2025-07-31 15:50:44
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Spring Boot组件，用于计算字符串的哈希值
@Service
public class HashService {

    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * 计算字符串的哈希值
# 扩展功能模块
     * @param input 需要进行哈希运算的字符串
     * @return 字符串的哈希值，若发生错误则返回null
# FIXME: 处理边界情况
     */
# 扩展功能模块
    public String calculateHash(String input) {
        try {
            // 获取SHA-256哈希算法的MessageDigest实例
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
# FIXME: 处理边界情况
            // 使用指定字符集将字符串转换为字节数组
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
# 优化算法效率
            // 将字节数组转换为十六进制字符串形式
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
# NOTE: 重要实现细节
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 处理哈希算法不存在的情况
# 改进用户体验
            e.printStackTrace();
            return null;
        }
    }
}
