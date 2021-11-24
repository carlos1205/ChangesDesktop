package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.model.User;
import com.change.client.repository.user.IUserDAO;
import com.change.client.repository.user.UserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Text welcome;

    public void handleLogout(ActionEvent event) {
        IUserDAO userDao = UserDAO.getInstance();
        userDao.logout();
        Storage.getInstance().setUserId("");
        StageFactory.getInstance().changeScene(EnumScenes.LOGIN);
    }

    @FXML
    public void initialize(){
        IUserDAO userDao = UserDAO.getInstance();
        User user = userDao.getUser(Storage.getInstance().getUserId());
        welcome.setText("Seja bem-vindo ao Change.\n");
    }
}
