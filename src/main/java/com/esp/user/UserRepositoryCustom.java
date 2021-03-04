package com.esp.user;

import com.esp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepositoryCustom extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

   /* @Query(value = "SELECT u FROM User u where u.username = ?1 and u.password = ?2 ")
    Optional login(String username,String password);
    Optional findByToken(String token);*/
}
