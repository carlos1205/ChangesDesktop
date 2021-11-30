package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class ForgetPassSendController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private TextField email;
    @FXML
    private Text errors;

    public void handleGoBack(ActionEvent event){
        this.clear();
        stageFactory.changeScene(EnumScenes.LOGIN);
    }

    public void handleSend(ActionEvent event){
        System.out.println("send!");
        System.out.println(event.getEventType());
        this.clear();
    }

    public static void setUserDao(IUserDAO userDao) {
        ForgetPassSendController.userDao = userDao;
    }

    public static void setStageFactory(StageFactory stageFactory) {
        ForgetPassSendController.stageFactory = stageFactory;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        this.email.setText("");
        this.errors.setText("");
    }
}
