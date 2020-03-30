package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerStats {

    private int acc;
    private int bell;
    private int cora;

    private int maxOn;
    private int avgOn;

    private int totalContas;
    private int totalPersonagens;
    private int personagensExistentes;
    private int personagensDeletados;

    public ServerStats() {
        this.acc = 0;
        this.cora = 0;
        this.bell = 0;

        this.maxOn = 0;
        this.avgOn = 0;

        this.totalContas = 0;
        this.totalPersonagens = 0;
        this.personagensExistentes = 0;
        this.personagensDeletados = 0;
    }

    @Override
    public String toString() {
        return "ServerStats{" +
                "acc=" + acc +
                ", bell=" + bell +
                ", cora=" + cora +
                ", maxOn=" + maxOn +
                ", avgOn=" + avgOn +
                ", totalContas=" + totalContas +
                ", totalPersonagens=" + totalPersonagens +
                ", personagensExistentes=" + personagensExistentes +
                ", personagensDeletados=" + personagensDeletados +
                '}';
    }
}
