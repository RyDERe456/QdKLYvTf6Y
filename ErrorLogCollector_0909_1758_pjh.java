// 代码生成时间: 2025-09-09 17:58:11
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 错误日志收集器，用于捕获和记录Spring Boot应用中的异常和错误。
 */
@Component
@RestControllerAdvice
public class ErrorLogCollector {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);

    /**
     * 异常处理方法，可以捕获并记录任何非预期的异常。
     *
     * @param exception 异常对象
     * @param request 当前请求
     * @return 自定义的错误信息
     */
    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest request, Exception exception) {
        // 记录错误日志，包括时间、URL、异常信息
        logger.error("{}
{}
{}", LocalDateTime.now(), request.getRequestURL(), exception.getMessage());

        // 可以根据需要返回自定义的错误信息，或者返回异常对象里的详细信息
        return "An error occurred: " + exception.getMessage();
    }
}
