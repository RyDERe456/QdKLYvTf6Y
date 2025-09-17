// 代码生成时间: 2025-09-18 00:18:57
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

// 用户权限管理系统组件
@Service
public class UserPermissionService {

    private final UserRepository userRepository;

    // 通过构造器注入UserRepository
    @Autowired
    public UserPermissionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 获取用户权限
    public List<String> getUserPermissions(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get().getPermissions();
    }

    // 添加权限给用户
    public void addUserPermission(Long userId, String permission) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.get().addPermission(permission);
        userRepository.save(user.get());
    }

    // 删除用户的权限
    public void removeUserPermission(Long userId, String permission) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.get().removePermission(permission);
        userRepository.save(user.get());
    }
}
