package br.com.rfreforged.ReforgedGCP.model.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlterarSenha {
    private int id;
    private String nomeUsuario;
    private String senhaAtual;
    private String novaSenha;
}
