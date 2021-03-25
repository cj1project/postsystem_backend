package com.esp.user;

import com.esp.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    User createEmptyUser();
    User getUser();
    List getUserList();

}
