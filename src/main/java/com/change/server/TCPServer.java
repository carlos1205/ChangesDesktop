package com.change.server;

import com.change.server.GUI.EnumScenes;
import com.change.server.GUI.StageFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TCPServer extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        StageFactory factory = StageFactory.getInstance(stage);
        stage.setTitle("Server");
        factory.changeScene(EnumScenes.INPUT);
    }

    public static void main(String[] args) {
        launch();
    }
}
