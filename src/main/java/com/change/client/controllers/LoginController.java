package com.change.client.controllers;

import com.change.Security.HashGenerator;
import com.change.client.EnumScenes;
import com.change.client.repository.user.IUserDAO;
import com.change.client.repository.user.UserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Text errors;

    public void handleLogin(ActionEvent actionEvent) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        IUserDAO userDao = UserDAO.getInstance();
        if(true == userDao.login(email.getText(), new HashGenerator().hashGenerate(password.getText()).toLowerCase())) {
            this.clear();
            StageFactory.getInstance().changeScene(EnumScenes.HOME);
        }else {
            setErrors(userDao.getErrors());
        }
    }

    public void handleCadastrar(ActionEvent actionEvent) {
        this.clear();
        StageFactory.getInstance().changeScene(EnumScenes.CADASTRO);
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
