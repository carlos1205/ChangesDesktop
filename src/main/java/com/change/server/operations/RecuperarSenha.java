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

public class RecuperarSenha extends IOperation{
    private static final EnumOperations operations = EnumOperations.RECUPERAR_SENHA;
    public RecuperarSenha(IOperation next){
        super(next);
    }

    public RecuperarSenha(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(parseJsonEmail(message), client, messages);
        }else{
            super.handle(client, message);
        }
    }

    private void make(String email, ClientConnection client, List<String> messages) throws IOException{
        if(UserDAO.getInstance().sendEmailRestorePass(email)) {
            messages.add("Email enviado");
            client.send(makeResponse(false, messages));
        }else{
            messages.add("Usuário não encontrado.");
            client.send(makeResponse(true, messages));
        }
        client.close();
    }

    private String parseJsonEmail(JSONObject message){
        return message.getJSONObject("data").getString("email");
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
