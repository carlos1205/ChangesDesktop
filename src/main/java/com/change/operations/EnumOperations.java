package com.change.operations;

public enum EnumOperations {
    LOGIN(1),
    LOGOUT(8),
    CADASTRO_USUARIO(5),
    EDICAO_USUARIO(10),
    DELETAR_USUARIO(13);

    private final int number;
    EnumOperations(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
