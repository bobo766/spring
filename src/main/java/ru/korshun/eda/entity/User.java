package ru.korshun.eda.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="users")
public class User
        implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "login")
    @Email
    private String login;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "name")
    private String username;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

//    public User(@NotBlank String login, @NotBlank String password, @NotBlank String name) {
//        this.login = login;
//        this.password = password;
//        this.name = name;
//    }

    private User(Long id, String name, String login, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = name;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public static User create(User user) {

        return new User(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                null
        );

    }


    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
