package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Inventario {

    private String nome;
    private List<Equipamento> equipamentos;

    public Inventario() {
        equipamentos = new ArrayList<>();
    }
}
