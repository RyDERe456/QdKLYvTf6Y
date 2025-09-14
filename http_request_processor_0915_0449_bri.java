// 代码生成时间: 2025-09-15 04:49:55
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HttpRequestProcessor {

    // Handler for GET requests
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    // Handler for POST requests
    @PostMapping("/process")
    public ResponseEntity<?> processRequest(@RequestBody String data) {
        // Process data
        return ResponseEntity.ok("Received data: " + data);
    }

    // Global exception handler
    @ControllerAdvice
    public class GlobalExceptionHandler {

        // Handle HttpMessageNotReadableException
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "Invalid request body: 