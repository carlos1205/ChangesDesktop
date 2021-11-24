package com.change.client.repository.user;

import com.change.model.User;
import com.change.client.service.ClientConnection;
import com.change.client.service.Storage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
            this.errors.add(response.getJSONArray("mensagem").getString(0));
            connection.close();
        }
        return !res;
    }

    public void logout(){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject send = new JSONObject().put("operacao", 8);

        connection.send(send.toString());
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
        return new JSONObject()
                .put("operacao", 1)
                .put("data", new JSONObject()
                        .put("email", email)
                        .put("senha", password)
                ).toString();
    }
}
