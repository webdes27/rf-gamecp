package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pontos {
    private float dinheiro;
    private float gold;
    private float cash;
    private float goldPoint;
    private float huntingPoint;
    private float processingPoint;
    private float pvpPoint;
    private String personagem;
}
