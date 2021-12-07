package com.change.client.controllers;

import com.change.Security.HashGenerator;
import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class EditUserController implements IMenuHandle{
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Text errors;

    @FXML
    public void initialize(){
        User user = Storage.getInstance().getUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
    }

    public void handleEdit(ActionEvent event) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(userDao.update(name.getText(), email.getText(), new HashGenerator().hashGenerate(password.getText()).toLowerCase())){
            User user = Storage.getInstance().getUser();
            user.setEmail(email.getText());
            user.setName(name.getText());
            StageFactory.getInstance().doEdit();
            stageFactory.changeScene(EnumScenes.HOME);
        }else{
            setErrors(userDao.getErrors());
        }
    }

    public void handleDelecao(ActionEvent event) {
        this.clear();
        stageFactory.changeScene(EnumScenes.EXCLUIR_USER);
    }

    public void handleGoHome(ActionEvent event){
        this.clear();
        stageFactory.changeScene(EnumScenes.HOME);
    }

    public static void setUserDao(IUserDAO userDao) {
        EditUserController.userDao = userDao;
    }

    public static void setStageFactory(StageFactory stageFactory) {
        EditUserController.stageFactory = stageFactory;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        this.password.setText("");
        this.errors.setText("");
    }

    @Override
    public void handleGoHome() {
        menu.handleGoHome();
    }

    @Override
    public void handleGoEdit() {
        menu.handleGoEdit();
    }

    @Override
    public void handleDelecao() {
        menu.handleDelecao();
    }

    @Override
    public void handleCadastrarItem() {
        menu.handleCadastrarItem();
    }
}
