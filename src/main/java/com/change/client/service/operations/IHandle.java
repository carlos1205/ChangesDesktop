package com.change.client.service.operations;

import org.json.JSONObject;
import java.io.IOException;

public abstract class IHandle {
    private IHandle next;

    public IHandle(IHandle next){
        this.next = next;
    }

    public IHandle(){
        this.next = null;
    }

    public void setNext(IHandle next){
        this.next = next;
    }

    public void handle(JSONObject message) throws IOException {
        if(null != next)
            next.handle(message);
    }
}
