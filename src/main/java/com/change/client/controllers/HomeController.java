package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Controller
public class HomeController implements IMenuHandle{
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;

    @FXML
    private Label welcome;
    @FXML
    private Label user;

    public void handleLogout(ActionEvent event) {
        userDao.logout();
        stageFactory.changeScene(EnumScenes.LOGIN);
    }

    @FXML
    public void initialize(){
        User user = Storage.getInstance().getUser();
        welcome.setText("Seja bem-vindo ao Change.");
        this.user.setText(user.getName());
    }

    public static void setUserDao(IUserDAO userDao) {
        HomeController.userDao = userDao;
    }

    public static void setStageFactory(StageFactory stageFactory) {
        HomeController.stageFactory = stageFactory;
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

    @Override
    public void handleListagem() {
        menu.handleListagem();
    }

    @Override
    public void handleGoMyItens() {
        menu.handleGoMyItens();
    }
}
