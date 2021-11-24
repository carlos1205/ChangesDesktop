package com.change.client.repository.user;

import com.change.model.User;
import com.change.client.service.ClientConnection;
import com.change.client.service.Storage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.json.*;

public class UserDAO implements IUserDAO{
    private static UserDAO instance;
    private final List<String> errors;

    private UserDAO(){
        errors = new ArrayList<>();
    }

    public static UserDAO getInstance(){
        if(null == instance)
            instance = new UserDAO();
        return instance;
    }

    public boolean login(String email, String password){
        ClientConnection connection = ClientConnection.getInstance();

        JSONObject response = connection.send(parseLoginToJson(email, password));
        boolean res = Boolean.parseBoolean(response.get("erro").toString());

        if(!res){
            Storage.getInstance().setUserId("asht7123x");
        }else{
            this.errors.add(response.get("mensagem").toString());
            connection.close();
        }
        return !res;
    }

    public void logout(){
        ClientConnection connection = ClientConnection.getInstance();

        String send = Json.createObjectBuilder()
                .add("operacao",8)
                .build().toString();

        connection.send(send);
        connection.close();
    }

    public  boolean cadastrar(String name, String email, String password){
        System.out.println("Cadastrado");
        return true;
    }

    public List<String> getErrors(){
        List<String> errs = new ArrayList<>(errors);
        errors.clear();
        return errs;
    }

    public User getUser(String id){
        if(id.equals("asht7123x"))
            return User.make("asht7123x", "Carlos de Souza", "carlos@gmail.com", "Ca120599");
        return null;
    }

    private String parseLoginToJson(String email, String password){
        return  Json.createObjectBuilder()
                    .add("operacao",1)
                    .add("data", Json.createObjectBuilder()
                            .add("email", email)
                            .add("password", password)
                            .build())
                    .build().toString();
    }
}
