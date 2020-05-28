package ru.korshun.eda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    @Column(name = "name")
    private String username;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Role roles;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

//    public User(@NotBlank String login, @NotBlank String password, @NotBlank String name) {
//        this.login = login;
//        this.password = password;
//        this.name = name;
//    }

    public User(Long id, String name, String login, String password, Role role) {
        this.id = id;
        this.username = name;
        this.login = login;
        this.password = password;
        this.roles = role;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRoles() {
        return roles;
    }

}
