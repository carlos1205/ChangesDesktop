package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.service.StageFactory;
import com.change.client.service.connection.ClientConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InitController {
    @FXML
    private TextField host;
    @FXML
    private TextField port;

    public void handleStart(ActionEvent actionEvent){
        String host = this.host.getText();
        ClientConnection.setHost(host);

        int port = Integer.parseInt(this.port.getText());
        ClientConnection.setPort(port);

        StageFactory.getInstance().changeScene(EnumScenes.LOGIN);
    }
}
