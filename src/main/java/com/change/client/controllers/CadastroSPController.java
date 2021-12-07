package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.service.StageFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Controller
public class CadastroSPController {
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private TextField title;
    @FXML
    private ComboBox category;
    @FXML
    private TextArea description;
    @FXML
    private TextField value;
    @FXML
    private ComboBox type;
    @FXML
    private RadioButton service;
    @FXML
    private RadioButton product;

    public void handleCadastrar(){
        System.out.println("Cadastrar");
    }

    public void handleGoBack(){
        stageFactory.changeScene(EnumScenes.HOME);
    }

    public void setStageFactory(StageFactory stageFactory){
        CadastroSPController.stageFactory = stageFactory;
    }

}
