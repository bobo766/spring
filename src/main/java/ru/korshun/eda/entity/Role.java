package ru.korshun.eda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Collection<User> users;

    public Role() {
    }

    public Role(@NotBlank String role) {
        this.role = role;
    }

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
