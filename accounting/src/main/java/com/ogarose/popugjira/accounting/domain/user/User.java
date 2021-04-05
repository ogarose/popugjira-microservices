package com.ogarose.popugjira.accounting.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.*;

/**
 * @todo separate UserDetails, OAuth2User and Domain User
 */
@Entity
@Getter
@EqualsAndHashCode
public class User implements UserDetails, OAuth2User {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    private Integer balance = 0;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions;

    public User() {
    }

    public User(UUID id, String name, Role role, String email, String phone) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public void update(String name, Role role, String email, String phone) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public void decreaseBalance(Integer sum) {
        balance -= Math.abs(sum);
    }

    public void increaseBalance(Integer sum) {
        balance += Math.abs(sum);
    }

    public void payBalance() {
        balance = 0;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return EnumSet.of(role);
    }

    @Override
    public String getPassword() {
        return "pass";
    }

    @Override
    public String getUsername() {
        return name;
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
    public String getName() {
        return null;
    }
}
