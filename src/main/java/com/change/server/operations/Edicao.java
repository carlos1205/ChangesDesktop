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

public class Edicao extends IOperation{
    private static final EnumOperations operations = EnumOperations.EDICAO_USUARIO;
    public Edicao(IOperation next){
        super(next);
    }

    public Edicao(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(parseJsonUser(message), client, messages);
        }else{
            super.handle(client, message);
        }
    }

    private void make(User user, ClientConnection client, List<String> messages) throws IOException{
        if(!IsValid(user)){
            messages.add("Nenhum campo pode ser vazio");
            client.send(makeResponse(true, messages));
            return;
        }

        user.setId(ClientsManager.getInstance().getId(client.getIP()));
        if(UserDAO.getInstance().editar(user)) {
            messages.add("Usuário atualizado.");
            client.send(makeResponse(false, messages));
        }else{
            messages.add("Usuário não encontrado.");
            client.send(makeResponse(true, messages));
        }
    }

    private boolean IsValid(User user){
        return !(user.getEmail().equals("") || user.getName().equals(""));
    }

    private User parseJsonUser(JSONObject message){
        User user = new User();
        user.setName(message.getJSONObject("data").getString("nome_usuario"));
        user.setEmail(message.getJSONObject("data").getString("email"));
        user.setPassword(message.getJSONObject("data").getString("senha"));
        return user;
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
