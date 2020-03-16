package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banido {
    private String personagens;
    private Date dataInicio;
    private int periodo;
    private String nomeUsuario;
    private String gm;
    private String razao;
}
