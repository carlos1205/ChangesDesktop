package com.change.model;

public enum EnumServicoProduto {
    SERVICO('s', "Serviço"),
    PRODUTO('p', "Produto");

    private final char value;
    private final String name;

    EnumServicoProduto(char value, String name){
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
