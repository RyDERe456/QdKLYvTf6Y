// 代码生成时间: 2025-09-03 04:59:17
package com.example.demo.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
# 增强安全性
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
# 扩展功能模块
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletRequest;
# NOTE: 重要实现细节
import java.util.HashMap;
import java.util.Map;

public class ApiResponseFormatter implements ResponseBodyAdvice<Object> {
# 改进用户体验

    // 此方法决定是否应用这个Advice
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // 应用到所有的响应
    }
# TODO: 优化性能

    // 在响应体写入前调用
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
# 改进用户体验
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("data", body);
        apiResponse.put("status", response.getStatusCode().value());
        apiResponse.put("timestamp", System.currentTimeMillis());
# 扩展功能模块
        return apiResponse;
    }

    // 自定义错误响应处理器
    public static class ErrorResponse {
        private String message;
        private int status;
        private long timestamp;

        public ErrorResponse(String message, int status) {
# 增强安全性
            this.message = message;
            this.status = status;
            this.timestamp = System.currentTimeMillis();
# NOTE: 重要实现细节
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
# 优化算法效率
            this.message = message;
        }

        public int getStatus() {
            return status;
        }
# NOTE: 重要实现细节

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
# 改进用户体验
    }
}
