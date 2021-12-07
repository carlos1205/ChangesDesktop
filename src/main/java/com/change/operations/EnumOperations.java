package com.change.operations;

public enum EnumOperations {
    LOGIN(1),
    LOGOUT(8),
    CADASTRO_USUARIO(5),
    EDICAO_USUARIO(10),
    DELETAR_USUARIO(13),
    RECUPERAR_SENHA(7),
    CADASTRO_ITEM(6),
    LISTAGEM(9),
    EDICAO_ITEM(11),
    DELETAR_ITEM(12);

    private final int number;
    EnumOperations(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
