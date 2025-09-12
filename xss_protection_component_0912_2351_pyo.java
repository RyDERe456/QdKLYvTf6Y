// 代码生成时间: 2025-09-12 23:51:46
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
# 增强安全性
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
# 改进用户体验
@ControllerAdvice
public class XssProtectionComponent extends ResponseEntityExceptionHandler {

    private static final String CONTENT_SECURITY_POLICY_HEADER = "X-Content-Security-Policy";
    private static final String CONTENT_SECURITY_POLICY_VALUE = "default-src 'self'; img-src 'self' data:; script-src 'self'; style-src 'self' 'unsafe-inline';";

    // 拦截所有请求，并添加CSP头部来防护XSS攻击
# FIXME: 处理边界情况
    @Override
    protected void afterBodyWrite(HttpServletRequest request, HttpServletResponse response, Object controller,
                                   HttpHeaders headers, ServletServerHttpRequest outputMessage) throws IOException {
        response.setHeader(CONTENT_SECURITY_POLICY_HEADER, CONTENT_SECURITY_POLICY_VALUE);
    }

    /**<ol>
     * 异常处理方法，当XSS攻击发生时返回相应的错误信息。
     *
     * @param ex 异常
     * @return 异常响应
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("XSS attack detected and blocked. Please do not input malicious scripts.", HttpStatus.OK);
    }
}
