package com.change.server.operations;

import com.change.Security.HashGenerator;
import com.change.model.User;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.UserDAO;
import com.change.server.service.ClientsManager;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
        if(Login(user, client)) {
            messages.add("Usuário Logado");
            client.send(makeResponse(false, messages));
        }else{
            messages.add("Email ou Senha inválido.");
            client.send(makeResponse(true, messages));
            client.close();
        }
    }

    private boolean Login(User user, ClientConnection client){
        User foundUser = UserDAO.getInstance().getUserWithEmail(user.getEmail());

        if((null != foundUser) && foundUser.getPassword().equalsIgnoreCase(user.getPassword())){
            ClientsManager.getInstance().addClient(client.getIP(), foundUser.getId());
            return true;
        }
        return false;
    }

    private User parseJsonUser(JSONObject message){
        User user = new User();
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
