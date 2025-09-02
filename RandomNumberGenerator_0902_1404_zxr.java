// 代码生成时间: 2025-09-02 14:04:27
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.Random;

@Component
@RestController
public class RandomNumberGenerator {

    private final Random random = new Random();

    /**
     * Generates a random number within a specified range.
     * 
     * @param min The minimum value of the range (inclusive).
     * @param max The maximum value of the range (exclusive).
     * @return ResponseEntity with the generated random number.
     */
    @GetMapping("/random")
    public ResponseEntity<Integer> generateRandomNumber(
            @RequestParam(name = "min", required = false, defaultValue = "0") Integer min,
            @RequestParam(name = "max", required = false, defaultValue = "100") Integer max) {
        
        if (max <= min) {
            return ResponseEntity.badRequest().body(null);
        }
        
        try {
            int randomNumber = random.nextInt(max - min) + min;
            return ResponseEntity.ok(randomNumber);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}