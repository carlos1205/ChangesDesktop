package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

public class ExcluirUserController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private Text errors;

    public void handleGoHome(ActionEvent event){
        this.clear();
        stageFactory.changeScene(EnumScenes.HOME);
    }

    public void handleGoEdit(ActionEvent event){
        this.clear();
        stageFactory.changeScene(EnumScenes.EDIT_USER);
    }

    public void handleExcluir(ActionEvent event){
        if(userDao.delete()){
            this.clear();
            stageFactory.changeScene(EnumScenes.LOGIN);
        } else{
            setErrors(userDao.getErrors());
        }
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        this.errors.setText("");
    }
}
