// 代码生成时间: 2025-09-21 12:48:40
import org.springframework.stereotype.Component;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RestController
public class AccessControlComponent {

    /**
     * A secured endpoint that requires the user to have the 'ROLE_ADMIN' authority.
     * @return A response indicating successful access or an error.
     */
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> adminEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Welcome, Admin: " + authentication.getName());
    }

    /**
     * An endpoint that requires the user to have specific authorities.
     * @return A response indicating successful access or an error.
     */
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> userEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Welcome, User: " + authentication.getName());
    }

    /**
     * Error handling for access denied scenarios.
     * @param exception The AccessDeniedException thrown when access is denied.
     * @return A response entity indicating access was denied.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(403).body("Access Denied: You do not have permission to access this resource.");
    }
}
