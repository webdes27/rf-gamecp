package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.PersonagemDAO;
import br.com.rfreforged.ReforgedGCP.model.Personagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("personagem")
@RestController
public class PersonagemController {

    @Autowired
    private PersonagemDAO personagemDAO;

    @GetMapping("/{nomeUsuario}")
    public List<Personagem> getPersonagem(@PathVariable String nomeUsuario) {
        return personagemDAO.getPersonagem(nomeUsuario);
    }

    @GetMapping("/equipamento/{nomePersonagem}")
    public Personagem buscaEquipamento(@PathVariable String nomePersonagem) {
        return personagemDAO.buscaEquipamento(nomePersonagem);
    }

}
