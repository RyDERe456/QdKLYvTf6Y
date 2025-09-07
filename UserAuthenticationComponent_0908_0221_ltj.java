// 代码生成时间: 2025-09-08 02:21:07
package com.example.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Component
public class UserAuthenticationComponent {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**<ol>
     * 验证用户是否已认证
     * @return true 如果用户已认证，否则 false
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**<ol>
     * 认证用户
     * @param username 用户名
     * @param password 密码
     * @return 认证后的 UserDetails 对象
     */
    public UserDetails authenticateUser(String username, String password) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return userDetails;
    }

    /**<ol>
     * 创建认证令牌
     * @param userDetails UserDetails 对象
     * @return 创建的认证令牌
     */
    public Authentication createAuthenticationToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**<ol>
     * 检查用户是否具有特定权限
     * @param authority 权限字符串
     * @return true 如果用户具有权限，否则 false
     */
    public boolean hasAuthority(String authority) {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals(authority)))
                .orElse(false);
    }
}
