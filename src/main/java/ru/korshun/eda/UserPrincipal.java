package ru.korshun.eda;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.korshun.eda.entity.User;

import java.util.Collection;
import java.util.Objects;

public class UserPrincipal
        implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private static final String USER_ROLE = "user";

    private UserPrincipal(Long id, String name, String login, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = name;
        this.email = login;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = null;
//        authorities.add(new SimpleGrantedAuthority(USER_ROLE));

        UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                null
        );

        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
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
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
