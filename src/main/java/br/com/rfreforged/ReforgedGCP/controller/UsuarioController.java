package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.UsuarioDAO;
import br.com.rfreforged.ReforgedGCP.exception.SenhaAtualIncorretaException;
import br.com.rfreforged.ReforgedGCP.model.usuario.AlterarSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("usuario")
@RestController
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PutMapping("/alterar-senha")
    @PreAuthorize("#usuario.id == authentication.principal.id")
    public boolean alterarSenha(@RequestBody AlterarSenha usuario) throws SenhaAtualIncorretaException {
        return usuarioDAO.alterarSenha(usuario);
    }
}
