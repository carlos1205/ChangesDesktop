package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import javafx.event.ActionEvent;

public class ExcluirUserController {
    @Inject
    private static IUserDAO userDao;
    @Inject
    private static StageFactory stageFactory;

    public void handleGoHome(ActionEvent event){
        stageFactory.changeScene(EnumScenes.HOME);
    }

    public void handleGoEdit(ActionEvent event){
        stageFactory.changeScene(EnumScenes.EDIT_USER);
    }

    public void handleExcluir(ActionEvent event){
        System.out.println("Excluir");
        userDao.logout();
        stageFactory.changeScene(EnumScenes.LOGIN);
    }
}
