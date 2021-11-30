package com.change.server.GUI;

public enum EnumScenes {
    INPUT("servidor-port.fxml"),
    RUN("servidor-run.fxml");

    private final String fileName;

    EnumScenes(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }
}
