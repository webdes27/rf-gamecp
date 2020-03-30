package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoCW {
    private Date dataInicio;
    private Date dataFim;
    private int tempoDecorrido;
    private String racaVencedora;
    private String racaPerdedora;
}
