// 代码生成时间: 2025-08-29 19:08:05
 * This Spring Data JPA repository interface provides a way to perform database operations
 * with the User entity. It includes basic CRUD operations and methods to prevent SQL injection.
 */

package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their email, using named parameters to prevent SQL injection.
     *
     * @param email The email of the user to find.
     * @return An Optional User entity if found, otherwise empty.
     */
    Optional<User> findByEmail(@Param("email") String email);
    
    /**
     * Custom query to find a user by their username, using named parameters to prevent SQL injection.
     *
     * @param username The username of the user to find.
     * @return An Optional User entity if found, otherwise empty.
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
