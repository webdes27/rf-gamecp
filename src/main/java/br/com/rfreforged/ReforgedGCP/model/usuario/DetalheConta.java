package br.com.rfreforged.ReforgedGCP.model.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalheConta {
    private String nome;
    private String email;
    private double cashPoint;
    private Date ultimoLogon;
    private Date ultimoLogoff;
    private boolean statusPersonagem;
    private Date dataFinalPremium;
    private boolean premium;

    @Override
    public String toString() {
        return "DetalheConta{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cashPoint=" + cashPoint +
                ", ultimoLogon=" + ultimoLogon +
                ", ultimoLogoff=" + ultimoLogoff +
                ", statusPersonagem=" + statusPersonagem +
                ", dataFinalPremium=" + dataFinalPremium +
                ", premium=" + premium +
                '}';
    }
}
