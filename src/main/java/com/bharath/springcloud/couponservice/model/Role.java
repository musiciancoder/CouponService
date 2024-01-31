package com.bharath.springcloud.couponservice.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID= 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles") // con mappedBy = "roles" le decimos que ocupe las mismas anotaciones que definimos en la clase User para  private Set<Role> roles;
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
