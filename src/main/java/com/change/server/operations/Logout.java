package com.change.server.operations;

import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.service.ClientsManager;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logout extends IOperation{
    private static final EnumOperations operations = EnumOperations.LOGOUT;
    public Logout(IOperation next){
        super(next);
    }

    public Logout(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(client, messages);
        }else{
            super.handle(client, message);
        }
    }

    private void make(ClientConnection client, List<String> messages) throws IOException{
        boolean hasError = true;
        if(logout(client)){
            messages.add("Sucesso");
            hasError = false;
        }else{
            messages.add("Falha ao realizar Logout");
        }
        client.send(makeResponse(hasError, messages));
        client.close();
    }

    private boolean logout(ClientConnection client){
        ClientsManager.getInstance().removeClient(client.getIP());
        return true;
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }

}
