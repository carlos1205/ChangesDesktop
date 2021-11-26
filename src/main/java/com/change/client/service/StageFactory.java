package com.change.client.service;

import com.change.client.EnumScenes;
import com.change.client.ChangeApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StageFactory {
    private static StageFactory instance;

    private final Stage stage;
    private final Map<EnumScenes, Scene> scenes;

    private StageFactory(Stage stage){
        this.stage = stage;
        this.scenes = new HashMap<>();
    }

    public static StageFactory getInstance(Stage stage) {
        if(null == instance) instance = new StageFactory(stage);

        return instance;
    }

    public static StageFactory getInstance() {
        return instance;
    }

    public void changeScene(EnumScenes scene){
        stage.setScene(getScene(scene));
        stage.show();
    }

    private Scene getScene(EnumScenes scene){
        Scene response = scenes.get(scene);
        if(null == response)
            response = this.buildScene(scene);

        return response;
    }

    private Scene buildScene(EnumScenes scene){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChangeApplication.class.getResource("views/"+scene.getFileName()));
            Scene newScene = new Scene(fxmlLoader.load(), 600, 400);
            scenes.put(scene, newScene);
            return newScene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
