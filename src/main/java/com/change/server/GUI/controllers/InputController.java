package com.change.server.GUI.controllers;

import com.change.server.GUI.EnumScenes;
import com.change.server.GUI.StageFactory;
import com.change.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InputController {
    @FXML
    private TextField porta;
    @FXML
    private Text error;

    public void handleStart(ActionEvent event){
        try{
            int port = Integer.parseInt(porta.getText());
            this.porta.setText("");
            Server.getInstance(port);
            StageFactory.getInstance().changeScene(EnumScenes.RUN);
            this.error.setText("");
        }catch (Exception e){
            error.setText("Digite uma porta Válida");
            System.out.println(e.getMessage());
        }
    }
}
