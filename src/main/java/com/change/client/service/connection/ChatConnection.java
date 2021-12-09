package com.change.client.service.connection;

import org.json.JSONObject;

import java.io.IOException;

public class ChatConnection extends Thread{
    @Override
    public void run(){
        while(true){
            try {
                JSONObject obj = ClientConnection.getInstance().receive();
                System.out.println("Open Chat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
