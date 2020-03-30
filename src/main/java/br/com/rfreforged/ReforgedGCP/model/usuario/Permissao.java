package br.com.rfreforged.ReforgedGCP.model.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Permissao implements GrantedAuthority {
    private int id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
