package com.change.server.operations;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Logout extends IOperation{
    public Logout(IOperation next){
        super(next);
    }

    public Logout(){
        super();
    }

    @Override
    public String handle(JSONObject message){
        List<String> messages = new ArrayList<>();
        if(8 == message.getInt("operacao")){
            if(logout()){
                messages.add("Sucesso");
                return makeResponse(false, messages);
            }
            messages.add("erro.generico");
            return makeResponse(true, messages);
        }else{
            return super.handle(message);
        }
    }

    public boolean logout(){
        return true;
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", 1);
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
