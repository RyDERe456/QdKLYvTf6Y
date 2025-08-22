// 代码生成时间: 2025-08-22 23:25:11
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// 定义组件，使用@Service注解标识为Spring Boot组件
@Service
public class RandomNumberGeneratorService {

    private static final Random random = new Random();

    public int generateRandomNumber(int min, int max) {
        if (min > max) {
            // 抛出IllegalArgumentException，如果最小值大于最大值
            throw new IllegalArgumentException("Invalid range: min should not be greater than max");
        }

        // 生成随机数
        return random.nextInt(max - min + 1) + min;
    }
}

// 定义控制器，使用@RestController注解标识为Spring Boot控制器
@RestController
public class RandomNumberController {

    private final RandomNumberGeneratorService randomNumberGeneratorService;

    // 使用@Autowired注解自动装配RandomNumberGeneratorService实例
    public RandomNumberController(RandomNumberGeneratorService randomNumberGeneratorService) {
        this.randomNumberGeneratorService = randomNumberGeneratorService;
    }

    // 定义GET请求处理方法，使用@GetMapping注解
    @GetMapping("/random")
    public ResponseEntity<Integer> getRandomNumber(@RequestParam("min") int min, @RequestParam("max") int max) {
        try {
            // 调用RandomNumberGeneratorService生成随机数
            return ResponseEntity.ok(randomNumberGeneratorService.generateRandomNumber(min, max));
        } catch (IllegalArgumentException e) {
            // 捕获并处理IllegalArgumentException，返回错误信息
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}