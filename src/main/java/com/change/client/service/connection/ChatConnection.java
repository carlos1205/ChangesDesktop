package com.change.client.service.connection;

import org.json.JSONObject;

import java.io.IOException;

public class ChatConnection extends Thread{
    @Override
    public void run(){
        while(true){
            try {
                JSONObject obj = ClientConnection.getInstance().receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
