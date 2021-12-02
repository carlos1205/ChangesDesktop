package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    @FXML
    private Label welcome;

    @FXML
    private Label user;

    public void handleLogout(ActionEvent event) {
        userDao.logout();
        stageFactory.changeScene(EnumScenes.LOGIN);
    }

    public void handleEdicao(ActionEvent event) {
        stageFactory.changeScene(EnumScenes.EDIT_USER);
    }

    public void handleDelecao(ActionEvent event) {
        stageFactory.changeScene(EnumScenes.EXCLUIR_USER);
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
}
