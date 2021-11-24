package com.change.server.operations;

import javax.json.Json;
import javax.json.JsonObject;

public class Logout extends IOperation{
    public Logout(IOperation next){
        super(next);
    }

    public Logout(){
        super();
    }

    @Override
    public String handle(JsonObject message){
        if(8 == Integer.valueOf(message.get("operacao").toString())){
            if(logout())
                return makeResponse(false, "sucesso");
            return makeResponse(true, "Email ou Senha inválido.");
        }else{
            return super.handle(message);
        }
    }

    public boolean logout(){
        return true;
    }

    private String makeResponse(boolean error, String messages){
        JsonObject json = Json.createObjectBuilder()
                .add("operacao", 8)
                .add("erro", error)
                .add("mensagem", messages)
                .build();
        return json.toString();
    }
}
