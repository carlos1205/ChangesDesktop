package com.change.server.operations;

public enum EnumOperations {
    LOGIN(1),
    LOGOUT(8);

    private final int number;
    EnumOperations(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
