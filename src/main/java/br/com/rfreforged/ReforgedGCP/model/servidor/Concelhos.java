package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Concelhos {

    private List<Concelho> acc;
    private List<Concelho> bell;
    private List<Concelho> cora;

}
