package com.change.model;

public enum EnumStatus {
    ABERTO('a', "Aberto"),
    FECHADO('f', "Fechado");

    private final char value;
    private final String name;

    EnumStatus(char value, String name){
        this.value = value;
        this.name = name;
    }

    public char getValue(){
        return value;
    }

    public String getName(){
        return name;
    }
}
