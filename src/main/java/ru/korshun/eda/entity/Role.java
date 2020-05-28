package ru.korshun.eda.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role
        implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String role;

    @OneToMany(mappedBy = "roles")
    private Collection<User> users;

    public int getId() {
        return id;
    }

    public Collection<User> getUsers() {
        return users;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
