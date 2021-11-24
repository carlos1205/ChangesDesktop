package com.change.server.operations;

import org.json.JSONObject;

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

    public String handle(JSONObject message){
        if(null != next)
            return next.handle(message);
        return makeResponse(true, "Operacao invalida", message.getInt("operacao"));
    }

    private String makeResponse(boolean error, String messages, int operation){
        return new JSONObject()
                .put("operacao", operation)
                .put("erro", error)
                .put("mensagem", messages)
                .toString();
    }
}
