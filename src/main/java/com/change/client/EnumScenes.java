package com.change.client;

public enum EnumScenes {
    LOGIN("login-view.fxml"),
    CADASTRO("cadastro-view.fxml"),
    HOME("home-view.fxml");

    private final String fileName;

    EnumScenes(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }
}
