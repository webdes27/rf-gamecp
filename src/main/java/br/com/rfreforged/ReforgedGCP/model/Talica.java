package br.com.rfreforged.ReforgedGCP.model;

public enum Talica {

    Keen("0"),
    Destruction("1"),
    Darkness("2"),
    Chaos("3"),
    Hatred("4"),
    Favor("5"),
    Wisdom("6"),
    ScredFire("7"),
    Belief("8"),
    Guard("9"),
    Glory("10"),
    Grace("11"),
    Mercy("12"),
    Blank("14");

    private final String num;

    Talica(String i) {
        this.num = i;
    }

    public String getNum() {
        return num;
    }

}
