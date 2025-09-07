// 代码生成时间: 2025-09-07 09:26:33
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;

@Service
public class HashCalculatorService {

    /*
     * 构造函数
     */
    public HashCalculatorService() {
    }

    /*
     * 计算哈希值
     * @param input 需要计算哈希值的字符串
     * @return 字符串的哈希值
     * @throws NoSuchAlgorithmException 如果指定的摘要算法不存在
     */
    public String calculateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /*
     * 初始化方法，确保组件被正确初始化
     * @throws Exception 如果初始化失败
     */
    @PostConstruct
    public void init() throws Exception {
        try {
            // 可以在这里执行一些初始化操作
            // 例如，检查算法是否可用
            MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("Hash algorithm initialization failed", e);
        }
    }
}
