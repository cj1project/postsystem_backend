package com.esp.security.models;

import com.google.common.collect.Sets;

import java.util.Set;

public enum UserRole {
    USER(Sets.newHashSet(UserPermission.USER_READ)),
    ADMIN(Sets.newHashSet(UserPermission.USER_READ, UserPermission.USER_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
