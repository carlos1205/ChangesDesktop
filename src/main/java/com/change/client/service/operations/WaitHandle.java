package com.change.client.service.operations;

import com.change.operations.EnumOperations;
import org.json.JSONObject;

import java.io.IOException;

public class WaitHandle extends IHandle{
    private static WaitHandle instance;

    public static WaitHandle getInstance(){
        if(null == instance)
            instance = new WaitHandle();
        return instance;
    }

    public static WaitHandle getInstance(IHandle next){
        if(null == instance)
            instance = new WaitHandle(next);
        return instance;
    }

    private WaitHandle(IHandle next){
        super(next);
    }

    private WaitHandle(){
        super();
    }

    private EnumOperations wait;

    private JSONObject json;

    @Override
    public void handle(JSONObject message) throws IOException {
        if(message.getInt("operacao") == wait.getNumber()){
            this.json = message;
        }else{
            super.handle(message);
        }
    }

    public JSONObject waitHandle(EnumOperations wait){
        this.wait = wait;
        json = null;
        synchronized (this){
            while(json == null){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }
}