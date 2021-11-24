package com.change.client;

import com.change.client.service.StageFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        StageFactory factory = StageFactory.getInstance(stage);
        stage.setTitle("Change");
        factory.changeScene(EnumScenes.LOGIN);
    }

    public static void main(String[] args) {
        launch();
    }
}