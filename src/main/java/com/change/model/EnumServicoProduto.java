package com.change.model;

public enum EnumServicoProduto {
    SERVICO('s'),
    PRODUTO('p');

    private final char value;

    EnumServicoProduto(char value){
        this.value = value;
    }

    public char getValue(){
        return value;
    }
}
