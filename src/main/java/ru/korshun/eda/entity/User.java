package ru.korshun.eda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Email
    private String login;

    @JsonIgnore
    @NotBlank
    private String password;

    @NotBlank
    @Column(name = "name")
    private String username;

    @NotBlank
    @Email
    private String phone;

    @JsonIgnore
    @OneToOne(targetEntity = Role.class)
    @JoinColumn(name = "id_role")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Ajax> ajax;

    public User() {
    }

    public User(String name, String login, String password, String phone, Role role) {
        this.username = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

}
