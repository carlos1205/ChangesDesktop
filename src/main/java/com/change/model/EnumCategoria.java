package com.change.model;

public enum EnumCategoria {
    LAZER("lazer"),
    LIMPEZA("limpeza"),
    MODA("moda");

    private final String name;

    EnumCategoria(String name){
        this.name = name;
    }

    public String getName(){
       return this.name;
    }
}
