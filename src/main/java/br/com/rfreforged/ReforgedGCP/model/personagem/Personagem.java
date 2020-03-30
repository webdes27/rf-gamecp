package br.com.rfreforged.ReforgedGCP.model.personagem;

import br.com.rfreforged.ReforgedGCP.model.equipamento.Equipamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Personagem {
    private String nome;
    private int nivel;
    private String raca;
    private Classe classe;
    private int tempoJogado;
    private String genero;
    private double dinheiro;
    private double ouro;
    private double ptContribuicao;
    private double ptCertos;
    private double ptOuro;
    private double ptCaca;
    private double ptProcessamento;
    private Equipamento peito;
    private Equipamento calca;
    private Equipamento luva;
    private Equipamento bota;
    private Equipamento elmo;
    private Equipamento escudo;
    private Equipamento arma;
    private Equipamento capa;
    private List<Equipamento> aneis;
    private List<Equipamento> amuletos;

    public Personagem() {
        aneis = new ArrayList<>();
        amuletos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Personagem{" +
                "nome='" + nome + '\'' +
                ", nivel=" + nivel +
                ", raca='" + raca + '\'' +
                ", classe=" + classe +
                ", tempoJogado=" + tempoJogado +
                ", genero='" + genero + '\'' +
                ", dinheiro=" + dinheiro +
                ", ouro=" + ouro +
                ", ptContribuicao=" + ptContribuicao +
                ", ptCertos=" + ptCertos +
                ", ptOuro=" + ptOuro +
                ", ptCaca=" + ptCaca +
                ", ptProcessamento=" + ptProcessamento +
                ", peito=" + peito +
                ", calca=" + calca +
                ", luva=" + luva +
                ", bota=" + bota +
                ", elmo=" + elmo +
                ", escudo=" + escudo +
                ", arma=" + arma +
                ", capa=" + capa +
                ", aneis=" + aneis +
                ", amuletos=" + amuletos +
                '}';
    }
}
