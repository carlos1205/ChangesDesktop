package com.change.server.operations;

import com.change.model.User;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.UserDAO;
import com.change.server.service.ClientsManager;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Delecao extends IOperation{
    private static final EnumOperations operations = EnumOperations.DELETAR_USUARIO;
    public Delecao(IOperation next){
        super(next);
    }

    public Delecao(){
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
        if(UserDAO.getInstance().deletar(ClientsManager.getInstance().getId(client.getIP()))) {
            messages.add("Usuário apagado");
            client.send(makeResponse(false, messages));
            ClientsManager.getInstance().removeClient(client.getIP());
            client.close();
        }else{
            messages.add("Usuário não encontrado.");
            client.send(makeResponse(true, messages));
        }
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
