package com.change.server.GUI.controllers;

import com.change.server.Server;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ShowRunController {
    @FXML
    private Text porta;

    @FXML
    public void initialize(){
        porta.setText(String.valueOf(Server.getInstance(0).getPort()));
    }

    public void handleStop(){
        System.exit(0);
    }
}
