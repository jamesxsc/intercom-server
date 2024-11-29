package uk.co.xsc.intercom.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    // TODO: mongodb work
    @Getter @Id
    private long id;

    @Getter
    private String email;

    private String password;

    private List<String> roles;

    public User(String username, String password) {
        this.email = username;
        this.password = password;
        roles = List.of("ROLE_USER");
    }

    public User(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
