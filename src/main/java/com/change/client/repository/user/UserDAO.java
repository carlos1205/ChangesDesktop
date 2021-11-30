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

    public boolean create(String name, String email, String password){
        boolean res = createOrUpdate(name, email, password, EnumOperations.CADASTRO_USUARIO);
        ClientConnection.getInstance().close();
        return res;
    }

    public boolean update(String name, String email, String password){
        return createOrUpdate(name, email, password, EnumOperations.EDICAO_USUARIO);
    }

    public boolean delete(){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject json = new JSONObject().put("operacao", EnumOperations.DELETAR_USUARIO.getNumber());
        json = connection.send(json.toString());
        boolean res = Boolean.parseBoolean(json.get("erro").toString());
        if(res)
            this.errors.add(json.getJSONArray("mensagem").getString(0));
        else
            connection.close();
        return !res;
    }

    public void logout(){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject send = new JSONObject().put("operacao", EnumOperations.LOGOUT.getNumber());

        connection.send(send.toString());
        connection.close();
    }

    public boolean forgetPass(String email){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject response = connection.send(parseForgotPassToJson(email));
        return extractResponse(response);
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

    private boolean createOrUpdate(String name, String email, String password, EnumOperations operation){
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject response = connection.send(parseCompleteUserToJson(name, email, password, operation.getNumber()));
        return extractResponse(response);
    }

    private boolean extractResponse(JSONObject json){
        boolean res = Boolean.parseBoolean(json.get("erro").toString());

        if(!res){
            this.success.add(json.getJSONArray("mensagem").getString(0));
        }else {
            this.errors.add(json.getJSONArray("mensagem").getString(0));
        }

        return !res;
    }

    private String parseLoginToJson(String email, String password){
        return new JSONObject()
                .put("operacao", EnumOperations.LOGIN.getNumber())
                .put("data", new JSONObject()
                        .put("email", email)
                        .put("senha", password)
                ).toString();
    }

    private String parseCompleteUserToJson(String name, String email, String password, int op){
        return new JSONObject()
                .put("operacao", op)
                .put("data", new JSONObject()
                        .put("nome_usuario", name)
                        .put("email", email)
                        .put("senha", password)
                ).toString();
    }

    private String parseForgotPassToJson(String email){
        return new JSONObject()
                .put("operacao", EnumOperations.RECUPERAR_SENHA.getNumber())
                .put("data", new JSONObject()
                        .put("email", email)
                ).toString();
    }
}
