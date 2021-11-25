package com.change.client;

import com.change.client.service.ClientConnection;
import com.change.client.service.StageFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ChangeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        StageFactory factory = StageFactory.getInstance(stage);
        stage.setTitle("Change");
        factory.changeScene(EnumScenes.LOGIN);
    }

    public static void main(String[] args) {
        config();
        launch();
    }

    private static void config(){
        Scanner in = new Scanner(System.in);

        System.out.print("Digite o IP do servidor: ");
        String host = in.nextLine();
        ClientConnection.setHost(host);

        System.out.print("Digite a porta: ");
        int port = Integer.valueOf(in.nextLine());
        ClientConnection.setPort(port);
    }
}