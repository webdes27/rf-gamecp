package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.ServerDAO;
import br.com.rfreforged.ReforgedGCP.model.servidor.*;
import br.com.rfreforged.ReforgedGCP.model.usuario.Banido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("server")
public class ServerController {

    @Autowired
    private ServerDAO serverDAO;

    @GetMapping("/estats")
    public ServerStats getEstatisticas() {
        return serverDAO.getEstatisticas();
    }

    @GetMapping("/num-online")
    public int getTotPlayerOnline() {
        return serverDAO.getTotPlayerOnline().orElse(0);
    }

    @GetMapping("/top-online")
    public List<TopOnline> getTopOnlines() {
        return serverDAO.getTopOnlines();
    }

    @GetMapping(value = {"/lista-banidos/","/lista-banidos/{parametroQuery}"})
    public List<Banido> getListaBanidos(@PathVariable(required = false) String parametroQuery) {
        return serverDAO.getListaBanidos(parametroQuery);
    }
    @GetMapping("/estatisticas-cw")
    public GuerraChipStats getGuerraChipStats() {
        return serverDAO.getGuerraChipStats();
    }
    @GetMapping("/historico-cw")
    public List<HistoricoCW> getHistoricoCW() {
        return serverDAO.getHistoricoCW();
    }
    @GetMapping("/concelhos")
    public Concelhos getConcelhos() {
        return serverDAO.getConcelhos();
    }
}
