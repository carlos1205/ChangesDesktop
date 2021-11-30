package com.change.server.GUI.controllers;

import com.change.server.GUI.EnumScenes;
import com.change.server.GUI.StageFactory;
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
        Server.getInstance(0).close();
        this.porta.setText("");
        StageFactory.getInstance().changeScene(EnumScenes.INPUT);
    }
}
