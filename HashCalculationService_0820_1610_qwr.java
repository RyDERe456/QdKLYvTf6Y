// 代码生成时间: 2025-08-20 16:10:51
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.security.MessageDigest;
# 增强安全性
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// 哈希值计算工具组件
# 改进用户体验
@Service
public class HashCalculationService {

    // 常量，定义支持的哈希算法
    private static final String[] supportedHashAlgorithms = { "SHA-256", "SHA-384", "SHA-512" };
# 优化算法效率

    // 构造函数
    public HashCalculationService() {
    }

    // 计算哈希值的方法
    public String calculateHash(String input, String algorithm) {
        if (!StringUtils.hasText(input)) {
            throw new IllegalArgumentException("输入内容不能为空");
# 添加错误处理
        }

        // 检查算法是否支持
        if (!isSupportedAlgorithm(algorithm)) {
            throw new IllegalArgumentException("不支持的哈希算法: " + algorithm);
        }

        // 使用指定算法计算哈希值
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节转换为Base64编码的字符串
# 扩展功能模块
            return Base64.getEncoder().encodeToString(messageDigest);
# 扩展功能模块
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希算法错误: " + e.getMessage(), e);
        }
    }

    // 检查算法是否支持
# TODO: 优化性能
    private boolean isSupportedAlgorithm(String algorithm) {
        for (String supportedAlgorithm : supportedHashAlgorithms) {
            if (supportedAlgorithm.equalsIgnoreCase(algorithm)) {
                return true;
            }
        }
        return false;
# 添加错误处理
    }

    // 确保组件初始化后，可以立即使用
    @PostConstruct
    public void init() {
        // 这里可以放置一些初始化代码，例如日志记录等
    }
}
