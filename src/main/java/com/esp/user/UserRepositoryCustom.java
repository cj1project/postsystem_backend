package com.esp.user;

import com.esp.models.Role;
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
    User getUserByUsername(@Param("username") String username);

    //@Query(value = "SELECT user.user_id, role.role_id, role.name FROM User user, Role role WHERE user.username ='admin' AND role.role_id = user.user_id")
  @Query(value = "SELECT NEW com.esp.classRepOfJoinedTables.UsersRoles(user, role)" +
            "FROM User user, Role role WHERE user.username =?1 AND role.role_id = user.user_id")
  User getUserByUserNameAndRole(@Param("username") String username);

   /* @Query(value = "SELECT u FROM User u where u.username = ?1 and u.password = ?2 ")
    Optional login(String username,String password);
    Optional findByToken(String token);*/
}
