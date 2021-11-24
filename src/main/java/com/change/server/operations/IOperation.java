package com.change.server.operations;

import javax.json.Json;
import javax.json.JsonObject;

public abstract class IOperation {
    private IOperation next;

    public IOperation(IOperation next){
        this.next = next;
    }

    public IOperation(){
        this.next = null;
    }

    public void setNext(IOperation next){
        this.next = next;
    }

    public String handle(JsonObject message){
        if(null != next)
            return next.handle(message);
        return makeResponse(true, "Operacao invalida", Integer.valueOf(message.get("operacao").toString()));
    }

    private String makeResponse(boolean error, String messages, int operation){
        JsonObject json = Json.createObjectBuilder()
                .add("operacao", operation)
                .add("erro", error)
                .add("mensagem", messages)
                .build();
        return json.toString();
    }
}
