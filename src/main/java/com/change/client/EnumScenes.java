package com.change.client;

public enum EnumScenes {
    LOGIN("login-view.fxml"),
    CADASTRO("cadastro-view.fxml"),
    HOME("home-view.fxml"),
    EDIT_USER("edicao-usuario-view.fxml"),
    EXCLUIR_USER("delete-user-view.fxml"),
    FORGET_PASS_SEND("forget-pass-send-view.fxml"),
    INIT_CLIENT("info-end-view.fxml"),
    CADASTRAR_SP("cadastro-sp-view.fxml"),
    LISTAGEM("listagem-view.fxml"),
    MY_ITENS("my-itens-view.fxml"),
    EDIT_ITEM("edicao-sp-view.fxml"),
    EXCLUIR_ITEM("delete-sp-view.fxml"),
    CHAT("chat-view.fxml"),
    FECHA_CHAT("fecha-chat.fxml");

    private final String fileName;

    EnumScenes(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }
}
