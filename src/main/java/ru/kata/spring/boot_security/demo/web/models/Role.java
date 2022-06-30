package ru.kata.spring.boot_security.demo.web.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String name) {
        this.role = name;
    }

    public void setRoleId() {
        if (role.equals("ROLE_ADMIN")) {
            this.setId(1);
        } else if (role.equals("ROLE_USER")) {
            this.setId(2);
        }
    }
}
