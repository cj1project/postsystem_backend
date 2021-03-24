package com.esp.classRepOfJoinedTables;

import com.esp.models.Role;
import com.esp.models.User;

public class UsersRoles {
    private User user;
    private Role role;

    public UsersRoles(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ResultFromDB{" +
                "role=" + role +
                ", user=" + user +
                '}';
    }
}
