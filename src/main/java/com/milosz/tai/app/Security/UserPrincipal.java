package com.milosz.tai.app.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.milosz.tai.app.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String nickname;

    private String email;

    private byte[] image;

    @JsonIgnore
    private String password;

    private String salt;

    private boolean active;

    private boolean banned;

    private Collection<? extends GrantedAuthority> authorities;


    public UserPrincipal(Long id, String nickname, String email, byte[] image, String password, String salt, boolean active, boolean banned, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.password = password;
        this.salt = salt;
        this.active = active;
        this.banned = banned;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getImage(),
                user.getPassword(),
                user.getSalt(),
                user.isActive(),
                user.isBanned(),
                authorities
        );
    }


    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
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
