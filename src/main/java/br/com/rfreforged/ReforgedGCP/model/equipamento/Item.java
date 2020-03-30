package br.com.rfreforged.ReforgedGCP.model.equipamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int codigo;
    private int tipo;
    private int slot;
    private Melhoria melhoria;

    @Override
    public String toString() {
        return "Item{" +
                "codigo=" + codigo +
                ", tipo=" + tipo +
                ", slot=" + slot +
                ", melhoria=" + melhoria +
                '}';
    }
}
