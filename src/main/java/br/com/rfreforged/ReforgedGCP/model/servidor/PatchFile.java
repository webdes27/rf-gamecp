package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchFile {

    private String path;
    private String hash;

}
