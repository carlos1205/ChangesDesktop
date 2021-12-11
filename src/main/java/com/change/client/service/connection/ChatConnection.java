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
                JSONObject obj = ClientConnection.getInstance().receive();
                OperationsFactory.getInstance().getOperations().handle(obj);
            } catch (IOException e) {
                System.out.println("Desconhectado");
            } catch (InterruptedException e) {
                instance = null;
            }
        }
    }

    public void close(){
        this.interrupt();
    }
}
