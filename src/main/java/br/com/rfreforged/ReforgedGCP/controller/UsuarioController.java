package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.UsuarioDAO;
import br.com.rfreforged.ReforgedGCP.exception.SenhaAtualIncorretaException;
import br.com.rfreforged.ReforgedGCP.model.AlterarSenha;
import br.com.rfreforged.ReforgedGCP.model.ApiResponse;
import br.com.rfreforged.ReforgedGCP.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("usuario")
@RestController
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping
    public ApiResponse registrar(Usuario usuario) {
        boolean b = usuarioDAO.criarConta(usuario);
        if (b) {
            return new ApiResponse(true, "Registrado com sucesso!");
        }
        return new ApiResponse(false, "Erro ao registrar!");
    }
    @PutMapping("/alterar-senha")
    public boolean alterarSenha(@RequestBody AlterarSenha usuario) throws SenhaAtualIncorretaException {
        return usuarioDAO.alterarSenha(usuario);
    }
}
