package ru.korshun.eda;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.korshun.eda.entity.User;

import java.util.*;

public class CustomUserDetails
        implements UserDetails {

    private final int id;

    private final String name;

    private final String username;

    private final String email;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private CustomUserDetails(int id, String name, String login, String password,
                              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = null;
        this.email = login;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority((user.getRoles()).getAuthority()));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                authorities
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
