// 代码生成时间: 2025-07-31 06:39:23
// XssProtectionComponent.java

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class XssProtectionComponent extends OncePerRequestFilter {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_HEADER = "text/html; charset=UTF-8";
    private static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";
    private static final String CONTENT_SECURITY_POLICY = "default-src 'self'; script-src 'self' https://*.googleapis.com;";
    private static final String REFLECTED_XSS_PROTECTION_HEADER = "X-XSS-Protection";
    private static final String REFLECTED_XSS_PROTECTION_VALUE = "1; mode=block";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 设置内容类型和字符集，防止IE执行HTML代码
        response.setContentType(CONTENT_TYPE_HEADER);

        // 设置内容安全策略，限制加载的资源和执行的脚本
        response.setHeader(CONTENT_SECURITY_POLICY_HEADER, CONTENT_SECURITY_POLICY);

        // 启用Chrome和Firefox的XSS过滤和阻止功能
        response.setHeader(REFLECTED_XSS_PROTECTION_HEADER, REFLECTED_XSS_PROTECTION_VALUE);

        // 继续执行过滤链
        filterChain.doFilter(request, response);
    }

    // 错误处理方法，用于记录和处理XSS攻击相关的错误
    public void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) {
        // 这里可以添加日志记录、错误通知等处理逻辑
        // 例如，记录攻击尝试、发送警告邮件等
        // 以下代码仅为示例，实际应用中应根据具体需求实现
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("An error occurred: " + e.getMessage());
    }
}
