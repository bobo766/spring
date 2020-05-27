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
    private int id;

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

    private User(int id, String name, String login, String password, Role role) {
        this.id = id;
        this.username = name;
        this.login = login;
        this.password = password;
        this.roles = role;
    }

    public static User create(User user) {

        return new User(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                user.getRoles()
        );

    }


    public int getId() {
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

    public Role getRoles() {
        return roles;
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
