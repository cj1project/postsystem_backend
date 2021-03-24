package com.esp.security.models;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    USER(Sets.newHashSet(Permission.USER_READ, Permission.USER_WRITE)),
    RESTRICTED_ADMIN(Sets.newHashSet(Permission.USER_READ, Permission.USER_WRITE, Permission.ADMIN_READ)),
    ADMIN(Sets.newHashSet(Permission.USER_READ, Permission.USER_WRITE, Permission.ADMIN_READ, Permission.ADMIN_WRITE));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority(("ROLE_" + this.name())));
        return permissions;
    }
}
