package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.PersonagemDAO;
import br.com.rfreforged.ReforgedGCP.exception.NomePersonagemException;
import br.com.rfreforged.ReforgedGCP.model.ApiResponse;
import br.com.rfreforged.ReforgedGCP.model.servidor.Pontos;
import br.com.rfreforged.ReforgedGCP.model.personagem.Inventario;
import br.com.rfreforged.ReforgedGCP.model.personagem.Personagem;
import br.com.rfreforged.ReforgedGCP.model.servidor.DarItens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/inventario/{nomePersonagem}")
    public Inventario getInventarioByNomePerso(@PathVariable String nomePersonagem) {
        return personagemDAO.getInventarioByNomePerso(nomePersonagem);
    }

    @PutMapping("/dar-pontos")
    public ApiResponse givePoints(@RequestBody Pontos pontos) {
        try {
            personagemDAO.givePoints(pontos);
            return new ApiResponse(true, "Pontos enviados com sucesso.");
        } catch (NomePersonagemException ex) {
            return new ApiResponse(false, ex.getMessage());
        }
    }

    @PutMapping("/dar-itens")
    public ApiResponse giveItens(@RequestBody DarItens darItens) {
        try {
            personagemDAO.giveItens(darItens);
            return new ApiResponse(true, "Item enviados com sucesso.");
        } catch (NomePersonagemException ex) {
            return new ApiResponse(false, ex.getMessage());
        } catch (IOException ex) {
            return new ApiResponse(false, "Ocorreu um erro ao enviar, tente novamente.");
        }

    }
}
