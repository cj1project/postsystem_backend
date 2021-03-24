package com.esp.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="roles")
public class Role implements Serializable {
    @Id
    @Column(length = 64)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String role_id;
    private String name;

    public Role() {
    }

    public String getId() {
        return role_id;
    }

    public void setId(String id) {
        this.role_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", name='" + name + '\'' +
                '}';
    }
}
