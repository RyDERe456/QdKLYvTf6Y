// 代码生成时间: 2025-08-06 23:30:36
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

// 主题切换控制器
@RestController
public class ThemeController {

    private static final String THEME_ATTRIBUTE = "theme";

    // 使用GET请求获取当前主题
    @GetMapping("/theme")
    public ResponseEntity<String> getCurrentTheme(HttpSession session) {
        String theme = (String) session.getAttribute(THEME_ATTRIBUTE);
        return ResponseEntity.ok(theme != null ? theme : "default");
    }

    // 使用POST请求设置当前主题
    @PostMapping("/theme\)
    public ResponseEntity<String> setTheme(@RequestParam("theme") String theme, HttpSession session) {
        try {
            if (theme == null || theme.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Theme cannot be null or empty");
            }
            session.setAttribute(THEME_ATTRIBUTE, theme);
            return ResponseEntity.ok("Theme set to: " + theme);
        } catch (Exception e) {
            // 错误处理，返回服务器内部错误
            return ResponseEntity.internalServerError().body("Error setting theme: " + e.getMessage());
        }
    }
}
