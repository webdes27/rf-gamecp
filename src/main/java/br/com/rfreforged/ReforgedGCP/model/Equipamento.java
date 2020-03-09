package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Equipamento {
    private int codigo;
    private String nome;
    private Melhoria melhoria;
    private int imgId;

    public Equipamento() {
        this.codigo = 0;
        this.nome = "Não Equipado.";
        this.melhoria = new Melhoria(0, Talica.Blank);
        this.imgId = -1;
    }
}
