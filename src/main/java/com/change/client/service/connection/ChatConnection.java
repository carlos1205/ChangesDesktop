package com.change.client.service.connection;

import com.change.client.service.OperationsFactory;
import org.json.JSONObject;

import java.io.IOException;

public class ChatConnection extends Thread{
    private static ChatConnection instance;

    public static ChatConnection getInstance(){
        if (null == instance)
            instance = new ChatConnection();
        return instance;
    }

    private ChatConnection(){}
    @Override
    public void run(){
        while(true){
            try {
                System.out.println("Lendo");
                synchronized (this){
                    this.wait();
                    JSONObject obj = ClientConnection.getInstance().receive();
                    OperationsFactory.getInstance().getOperations().handle(obj);
                }
            } catch (IOException | InterruptedException e) {
                instance = null;
            }
        }
    }
}
