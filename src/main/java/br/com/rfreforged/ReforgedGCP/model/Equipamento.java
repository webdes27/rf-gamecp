package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Equipamento {
    private int codigo;
    private int slot;
    private int qtd;
    private String nome;
    private Melhoria melhoria;
    private int imgId;

    public Equipamento() {
        this.codigo = 0;
        this.nome = "Não Equipado.";
        this.melhoria = new Melhoria();
        this.imgId = -1;
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", melhoria=" + melhoria +
                ", imgId=" + imgId +
                '}';
    }
}
