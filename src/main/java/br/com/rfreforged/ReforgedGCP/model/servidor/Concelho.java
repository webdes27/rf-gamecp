package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concelho {

    private String raca;
    private String nomePersonagem;
    private String posicao;
    private boolean status;

}
