// 代码生成时间: 2025-10-09 17:26:01
package com.example.licensemanagement;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/licenses") // 定义API的基础路径
@Component
public class LicenseManagementComponent {

    private final LicenseService licenseService;

    @Autowired // 自动注入LicenseService实例
    public LicenseManagementComponent(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/check") // 定义GET请求来检查许可证
    public ResponseEntity<Map<String, Object>> checkLicense(String licenseKey) {
        try {
            // 调用服务层检查许可证有效性
            boolean isValid = licenseService.validateLicense(licenseKey);
            Map<String, Object> response = new HashMap<>();
            response.put("isValid", isValid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 错误处理逻辑
            return handleException(e);
        }
    }

    // 错误处理器，用于统一处理异常
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
