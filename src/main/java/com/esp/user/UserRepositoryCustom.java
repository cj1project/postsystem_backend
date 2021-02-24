package com.esp.user;

import com.esp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepositoryCustom extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

   /* @Query(value = "SELECT u FROM User u where u.username = ?1 and u.password = ?2 ")
    Optional login(String username,String password);
    Optional findByToken(String token);*/
}
