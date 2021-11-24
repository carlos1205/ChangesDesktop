package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.repository.user.IUserDAO;
import com.change.client.repository.user.UserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class CadastroController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Text errors;

    public void handleCadastrar(ActionEvent event){
        IUserDAO userDao = UserDAO.getInstance();
        if(true == userDao.cadastrar(name.getText(), email.getText(), password.getText())){
            this.clear();
            StageFactory.getInstance().changeScene(EnumScenes.LOGIN);
        }else{
            setErrors(userDao.getErrors());
        }
    }

    public void handleGoBack(ActionEvent event){
        this.clear();
        StageFactory.getInstance().changeScene(EnumScenes.LOGIN);
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        this.name.setText("");
        this.email.setText("");
        this.password.setText("");
        this.errors.setText("");
    }
}
