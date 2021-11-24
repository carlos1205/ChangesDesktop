package com.change.server.operations;

import com.change.model.User;
import org.json.JSONObject;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Login extends IOperation{

    public Login(IOperation next){
        super(next);
    }

    public Login(){
        super();
    }

    @Override
    public String handle(JsonObject message){
        List<String> messages = new ArrayList<>();
        if(1 == Integer.valueOf(message.get("operacao").toString())){
            User user = parseJsonUser(message);
            if(Login(user.getEmail(), user.getPassword())) {
                messages.add("retorno.sucesso");
                return makeResponse(false, messages);
            }
            messages.add("erro.credenciais_invalidas");
            messages.add("erro.generico");
            return makeResponse(true, messages);
        }else{
            return super.handle(message);
        }
    }

    public boolean Login(String email, String password){
        String passMock = "90C262E87EA47FB07A75957D42121ABC362B2624C0AA0AD924BC1E4929154D2A";
        String emailMock = "carlos@gmail.com";

        if(email.equals(emailMock) && passMock.equals(password))
            return true;
        return false;
    }

    private User parseJsonUser(JsonObject message){
        User user = new User();
        user.setEmail(message.get("data").asJsonObject().get("email").toString().replace("\"", ""));
        user.setPassword(message.get("data").asJsonObject().get("password").toString().replace("\"", ""));
        return user;
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", 1);
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
