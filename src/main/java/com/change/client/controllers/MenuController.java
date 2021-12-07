package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.service.StageFactory;

public class MenuController implements IMenuHandle{
    private static StageFactory stageFactory;

    private static MenuController instance;

    public static MenuController getInstance(){
        if(null == instance)
            instance = new MenuController();

        return instance;
    }

    private MenuController(){
        stageFactory = StageFactory.getInstance();
    }

    public void handleGoHome(){
        stageFactory.changeScene(EnumScenes.HOME);
    }

    public void handleGoEdit(){
        stageFactory.changeScene(EnumScenes.EDIT_USER);
    }

    public void handleDelecao() {
        stageFactory.changeScene(EnumScenes.EXCLUIR_USER);
    }

    public void setStageFactory(StageFactory stage){
        stageFactory = stage;
    }
}
