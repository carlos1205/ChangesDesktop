package com.change.client.service.connection;

import com.change.client.service.OperationsFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatConnection extends Thread{

    @Override
    public void run(){
        while(true){
            try {
                JSONObject obj = ClientConnection.getInstance().receive();
                if(null != obj)
                    OperationsFactory.getInstance().getOperations().handle(obj);
            } catch (IOException | InterruptedException | JSONException e) {
                System.out.println("disconnect");
            }
        }
    }

    public void close(){
        this.interrupt();
    }
}
