// 代码生成时间: 2025-08-20 06:05:19
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class HttpHandlerComponent {

    // HTTP GET endpoint
    @GetMapping("/health")
    public String healthCheck() {
        return "Service is up and running";
    }

    // HTTP POST endpoint that accepts a JSON payload
    @PostMapping("/data")
    public ResponseEntity<String> processData(@RequestBody String data) {
        // Process the data
        // For simplicity, we just return the data back
        return ResponseEntity.ok("Received data: " + data);
    }

    // Exception handler for handling bad requests
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleBadRequest(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getReason());
    }

    // Generic exception handler for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Log the exception details
        // In a real-world scenario, you would use a logging framework
        System.err.println("Error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
