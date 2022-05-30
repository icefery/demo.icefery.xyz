package xyz.icefery.demo.security.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetails 实现类
 */
public class SecurityUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> grantedAuthorityList;

    public SecurityUserDetails(User user, List<Role> roleList) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.grantedAuthorityList = roleList
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
