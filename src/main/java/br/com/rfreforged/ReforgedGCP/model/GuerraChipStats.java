package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuerraChipStats {
    private int bwins;
    private int cwins;
    private int awins;
    private int bloses;
    private int closes;
    private int aloses;
    private int draw;
}
