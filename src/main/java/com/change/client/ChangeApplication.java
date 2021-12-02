package com.change.client;

import com.change.client.config.Config;
import com.change.client.service.StageFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        StageFactory factory = StageFactory.getInstance(stage);
        Config.getInstance().make();
        stage.setTitle("Change");
        factory.changeScene(EnumScenes.INIT_CLIENT);
    }

    public static void main(String[] args) {
        launch();
    }
}