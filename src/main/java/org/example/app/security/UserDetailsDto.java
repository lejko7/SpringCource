package org.example.app.security;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.web.dto.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsDto implements UserDetails {

    String username;
    String password;
    Boolean nonExpired;
    Boolean nonLocked;
    Boolean credentialsNonExpired;
    Boolean isEnabled;
    Collection<ERole> roles;

    public UserDetailsDto(String username, String password){
        this.username = username;
        this.password = password;
        this.nonExpired = true;
        this.nonLocked = true;
        this.credentialsNonExpired = true;
        this.isEnabled = true;
        this.roles = Arrays.asList(ERole.values());
    }

    public UserDetailsDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nonExpired = true;
        this.nonLocked = true;
        this.credentialsNonExpired = true;
        this.isEnabled = true;
        this.roles = Arrays.asList(ERole.values());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
