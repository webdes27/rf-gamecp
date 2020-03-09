package br.com.rfreforged.ReforgedGCP.security;

import br.com.rfreforged.ReforgedGCP.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserPrincipal implements UserDetails {

    private final Usuario usuario;

    public CustomUserPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonIgnore
    public int getId() {
        return usuario.getId();
    }

    @Override
    public String getUsername() {
        return usuario.getNome();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(usuario.getRole());
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}