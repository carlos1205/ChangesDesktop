package com.change.client.repository.user;

import com.change.model.User;
import com.change.client.service.connection.ClientConnection;
import com.change.client.service.Storage;
import com.change.operations.EnumOperations;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserDAO implements IUserDAO{
    private static UserDAO instance;
    private final List<String> errors;
    private final List<String> success;

    private UserDAO(){
        errors = new ArrayList<>();
        success = new ArrayList<>();
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

    public  boolean cadastrar(String name, String email, String password){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject response = connection.send(parseCadastroToJson(name, email, password));
        boolean res = Boolean.parseBoolean(response.get("erro").toString());

        if(!res){
            this.success.add(response.getJSONArray("mensagem").getString(0));
            connection.close();
        }else {
            this.errors.add(response.getJSONArray("mensagem").getString(0));
            connection.close();
        }
        return !res;
    }

    public void logout(){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject send = new JSONObject().put("operacao", EnumOperations.LOGOUT.getNumber());

        connection.send(send.toString());
        connection.close();
    }

    public List<String> getErrors(){
        List<String> errs = new ArrayList<>(errors);
        errors.clear();
        return errs;
    }

    public List<String> getSuccess(){
        List<String> sucs = new ArrayList<>(success);
        success.clear();
        return sucs;
    }

    private String parseLoginToJson(String email, String password){
        return new JSONObject()
                .put("operacao", EnumOperations.LOGIN.getNumber())
                .put("data", new JSONObject()
                        .put("email", email)
                        .put("senha", password)
                ).toString();
    }

    private String parseCadastroToJson(String name, String email, String password){
        return new JSONObject()
                .put("operacao", EnumOperations.CADASTRO_USUARIO.getNumber())
                .put("data", new JSONObject()
                        .put("nome_usuario", name)
                        .put("email", email)
                        .put("senha", password)
                ).toString();
    }

    public User getUser(String id){
        if(id.equals("asht7123x"))
            return User.make("asht7123x", "Carlos de Souza", "carlos@gmail.com", "Ca120599");
        return null;
    }
}
