package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

@Controller
public class ExcluirUserController implements IMenuHandle{
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;

    @FXML
    private Text errors;

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
