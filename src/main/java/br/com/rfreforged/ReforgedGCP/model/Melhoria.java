package br.com.rfreforged.ReforgedGCP.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
public class Melhoria {

    private final Talica[] talica;

    public Melhoria() {
        this.talica = new Talica[]{Talica.Blank,Talica.Blank,Talica.Blank,
                Talica.Blank,Talica.Blank,Talica.Blank,Talica.Blank};
    }

    @Override
    public String toString() {
        return "Melhoria{" +
                "talica=" + Arrays.toString(talica) +
                '}';
    }
}
