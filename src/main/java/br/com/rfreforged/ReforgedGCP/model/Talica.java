package br.com.rfreforged.ReforgedGCP.model;

public enum Talica {

    Blank("15"),
    Keen("0"),
    Destruction("1"),
    Darkness("2"),
    Chaos("3"),
    Favor("5"),
    Grace("11"),
    Mercy("12");

    private final String num;

    Talica(String i) {
        this.num = i;
    }

    public String getNum() {
        return num;
    }

}