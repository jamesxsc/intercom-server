package uk.co.xsc.intercom.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
public class User implements UserDetails {

    @Getter
    @Id
    private long id;

    @Getter
    private String email;

    private String password;

    private List<String> roles;

    @Getter
    // TODO look into DBRef not working

    // TODO check cascade deletion/be careful where we delete
    private List<PhoneNumber> phoneNumbers;

    // Constructor for registration
    public User(String username, String password) {
        this.email = username;
        this.password = password;
        roles = List.of("ROLE_USER");
    }

    // Constructors for Spring Data instantiation/cloning
    public User() {
    }

    public User(long id, String email, String password, List<String> roles, List<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.phoneNumbers = phoneNumbers;
    }

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

    public User hidePassword() {
        return new User(id, email, null, roles, phoneNumbers);
    }
}
