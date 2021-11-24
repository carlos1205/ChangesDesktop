package com.change.server.operations;

import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
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
    public String handle(JsonObject message){
        List<String> messages = new ArrayList<>();
        if(8 == Integer.valueOf(message.get("operacao").toString())){
            if(logout()){
                messages.add("retorno.sucesso");
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
