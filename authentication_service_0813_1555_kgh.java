// 代码生成时间: 2025-08-13 15:55:03
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // Constructor with dependency injection
    public AuthenticationService(AuthenticationManager authenticationManager,
                             UserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate a user and return a response
     * 
     * @param username Username of the user
     * @param password Password of the user
     * @return ResponseEntity with authentication result
     */
    public ResponseEntity<?> authenticate(String username, String password) {
        try {
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Check if password matches
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // If authentication is successful, return a success response
            return ResponseEntity.ok("Authentication successful");
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }
}