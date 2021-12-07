package com.change.model;

public enum EnumVendaDoacaoTroca {
    VENDA('v', "Venda"),
    DOACAO('d', "Doação"),
    TROCA('t', "Troca");

    private final char value;
    private final String name;

    EnumVendaDoacaoTroca(char value, String name){
        this.value = value;
        this.name = name;
    }

    public char getValue(){
        return value;
    }
}
