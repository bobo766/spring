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

    private final String phone;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private CustomUserDetails(int id, String name, String phone, String password,
                              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority((user.getRole()).getAuthority()));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPhone(),
                user.getPassword(),
                authorities
        );
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String getUsername() {
        return name;
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
