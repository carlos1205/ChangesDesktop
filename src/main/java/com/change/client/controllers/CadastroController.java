package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class CadastroController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Text errors;

    public void handleCadastrar(ActionEvent event){
        if(userDao.cadastrar(name.getText(), email.getText(), password.getText())){
            this.clear();
            stageFactory.changeScene(EnumScenes.LOGIN);
        }else{
            setErrors(userDao.getErrors());
        }
    }

    public void handleGoBack(ActionEvent event){
        this.clear();
        stageFactory.changeScene(EnumScenes.LOGIN);
    }

    public static void setUserDao(IUserDAO userDao) {
        CadastroController.userDao = userDao;
    }

    public static void setStageFactory(StageFactory stageFactory) {
        CadastroController.stageFactory = stageFactory;
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

    public TextField getName() {
        return name;
    }

    public TextField getEmail() {
        return email;
    }

    public PasswordField getPassword() {
        return password;
    }

    public Text getErrors() {
        return errors;
    }
}
