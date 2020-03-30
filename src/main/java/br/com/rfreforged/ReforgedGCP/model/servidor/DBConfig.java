package br.com.rfreforged.ReforgedGCP.model.servidor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBConfig {
    private DBConnection billing;
    private DBConnection rfUser;
    private DBConnection rfWorld;
    private DBConnection gamecp;

    @Override
    public String toString() {
        return "DBConfig{" +
                "billing=" + billing +
                ", rfUser=" + rfUser +
                ", rfWorld=" + rfWorld +
                ", gamecp=" + gamecp +
                '}';
    }
}
