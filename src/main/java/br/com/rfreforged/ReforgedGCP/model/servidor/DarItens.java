package br.com.rfreforged.ReforgedGCP.model.servidor;

import br.com.rfreforged.ReforgedGCP.model.equipamento.Talica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DarItens {
    private String codigo;
    private int tipo;
    private String planilha;
    private String personagem;
    private int qtd;
    private int slot;
    private int grade;
    private Talica talica;
    private int expira;
}
