package br.com.rfreforged.ReforgedGCP.security;

import br.com.rfreforged.ReforgedGCP.dao.UsuarioDAO;
import br.com.rfreforged.ReforgedGCP.model.usuario.Usuario;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {

        Usuario usuario = usuarioDAO.getUsuario(nomeUsuario);

        if (usuario == null) {
            throw new UsernameNotFoundException("Nenhum usuário encontrado com este nome de usuário " + nomeUsuario);
        } else {
            return new CustomUserPrincipal(usuario);
        }
    }
}