package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.InfoContaDAO;
import br.com.rfreforged.ReforgedGCP.model.usuario.DetalheConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("info")
@RestController
public class InfoContaController {

    @Autowired
    private InfoContaDAO infoContaDAO;

    @GetMapping("/conta/{nomeUsuario}")
    public DetalheConta getDetalheConta(@PathVariable String nomeUsuario) {
        return infoContaDAO.getDetalheConta(nomeUsuario);
    }
}
