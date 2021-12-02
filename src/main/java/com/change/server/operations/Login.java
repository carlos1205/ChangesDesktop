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

public class Login extends IOperation{
    private static final EnumOperations operations = EnumOperations.LOGIN;
    public Login(IOperation next){
        super(next);
    }

    public Login(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException{
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(parseJsonUser(message), client, messages);
        }else{
            super.handle(client, message);
        }
    }

    private void make(User user, ClientConnection client, List<String> messages) throws IOException{
        User credentials = Login(user, client);
        if(credentials != null) {
            messages.add("Usuário Logado");
            client.send(makeResponse(false, messages, credentials));
        }else{
            messages.add("Email ou Senha inválido.");
            client.send(makeResponse(true, messages));
            client.close();
        }
    }

    private User Login(User user, ClientConnection client){
        User foundUser = UserDAO.getInstance().getUserWithEmail(user.getEmail());

        if((null != foundUser) && foundUser.getPassword().equalsIgnoreCase(user.getPassword())){
            ClientsManager.getInstance().addClient(client.getIP(), foundUser.getId());
            return foundUser;
        }
        return null;
    }

    private User parseJsonUser(JSONObject message){
        User user = new User();
        user.setEmail(message.getJSONObject("data").getString("email"));
        user.setPassword(message.getJSONObject("data").getString("senha"));
        return user;
    }

    private String makeResponse(boolean error, List<String> messages, User user){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        obj.put("data", new JSONObject()
                .put("nome_usuario", user.getName())
                .put("email", user.getEmail())
                .put("uuid", user.getId())
        );
        return obj.toString();
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
