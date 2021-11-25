package com.change.client.controllers;

import com.change.Security.HashGenerator;
import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.repository.user.UserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LoginController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Text errors;

    public void handleLogin(ActionEvent actionEvent) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(true == userDao.login(email.getText(), new HashGenerator().hashGenerate(password.getText()).toLowerCase())) {
            this.clear();
            stageFactory.changeScene(EnumScenes.HOME);
        }else {
            setErrors(userDao.getErrors());
        }
    }

    public void handleCadastrar(ActionEvent actionEvent) {
        this.clear();
        stageFactory.changeScene(EnumScenes.CADASTRO);
    }

    public static void setUserDao(IUserDAO userDao) {
        LoginController.userDao = userDao;
    }

    public static void setStageFactory(StageFactory stageFactory) {
        LoginController.stageFactory = stageFactory;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        email.setText("");
        password.setText("");
        errors.setText("");
    }
}
