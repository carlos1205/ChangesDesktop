package com.change.model;

public enum EnumStatus {
    ABERTO('a'),
    FECHADO('f');

    private final char value;

    EnumStatus(char value){
        this.value = value;
    }

    public char getValue(){
        return value;
    }
}
