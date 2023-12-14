package com.example.demo.models;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //redundant variable...?
    private String username;

    private String password;
    private String email;

    @Enumerated (EnumType.STRING)
    private Role permissions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    // getters and setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }


    // as this is a method used in UserDetails we need to override it
    // additionally it will now point to an email instead of the username variable
    // consider the usefullness of the username variable
    @Override
    public String getUsername(){
        return this.email;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    // as this is a method used in UserDetails we need to override it
    @Override
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public Role getPermissions(){
        return this.permissions;
    }
    public void setPermissions(Role permissions){
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(permissions.name()));
        // throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
         //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
         //throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
    }

    @Override
    public boolean isEnabled() {
        return true;
         //throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }

}
