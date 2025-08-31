// 代码生成时间: 2025-08-31 18:35:05
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// 用户权限管理系统组件
@Service
public class UserPermissionService {

    @Autowired
    private UserRepository userRepository; // 假设有一个UserRepository用于数据交互

    // 获取当前登录用户的所有权限
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/permissions")
    public ResponseEntity<List<String>> getUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 假设userRepository中有方法getPermissionsByUsername获取用户权限列表
        List<String> permissions = userRepository.getPermissionsByUsername(authentication.getName());
        return ResponseEntity.ok(permissions);
    }

    // 添加用户权限
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/permissions")
    public ResponseEntity<Void> addPermission(@RequestParam String username, @RequestParam String permission) {
        if (!userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(null); // 用户不存在
        }

        // 假设userRepository中有方法addPermissionByUsername添加用户权限
        userRepository.addPermissionByUsername(username, permission);
        return ResponseEntity.ok().build();
    }

    // 删除用户权限
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/permissions/{permission}")
    public ResponseEntity<Void> deletePermission(@RequestParam String username, @PathVariable String permission) {
        if (!userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(null); // 用户不存在
        }

        // 假设userRepository中有方法removePermissionByUsername删除用户权限
        userRepository.removePermissionByUsername(username, permission);
        return ResponseEntity.ok().build();
    }

    // 错误处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
    }
}
