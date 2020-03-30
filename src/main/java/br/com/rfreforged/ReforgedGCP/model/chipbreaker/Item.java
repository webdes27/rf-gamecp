package br.com.rfreforged.ReforgedGCP.model.chipbreaker;

import br.com.rfreforged.ReforgedGCP.model.equipamento.Talica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String code;
    private int amount;
    private Talica talic;
    private int slots;
    private int grade;
}
