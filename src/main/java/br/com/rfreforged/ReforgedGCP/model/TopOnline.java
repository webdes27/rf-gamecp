package br.com.rfreforged.ReforgedGCP.model;

import br.com.rfreforged.ReforgedGCP.utils.EquipamentHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopOnline {

    private int rank;
    private String status;
    private String raca;
    private int nivel;
    private String nomePersonagem;
    private Classe classe;
    private int tempoJogo;
    private float ptContribuicao;
    private String guilda;
    private int racaNum;
    private String classeString;

    public void setRacaNum(int racaNum) {
        this.raca = EquipamentHelper.getRaca(racaNum);
    }

    public void setClasseString(String classeString) {
        this.classe = Stream.of(Classe.values()).filter(c -> c.getClasse().equals(classeString)).findFirst().orElseThrow();
    }
}
