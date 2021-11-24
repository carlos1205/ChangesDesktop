package com.change.server.operations;

import com.change.model.User;
import org.json.JSONObject;

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
    public String handle(JSONObject message){
        List<String> messages = new ArrayList<>();
        if(1 == message.getInt("operacao")){
            User user = parseJsonUser(message);
            if(Login(user.getEmail(), user.getPassword())) {
                messages.add("Sucesso");
                return makeResponse(false, messages);
            }
            messages.add("Email ou Senha invalido");
            messages.add("erro.generico");
            return makeResponse(true, messages);
        }else{
            return super.handle(message);
        }
    }

    public boolean Login(String email, String password){
        String passMock = "90C262E87EA47FB07A75957D42121ABC362B2624C0AA0AD924BC1E4929154D2A";
        String emailMock = "carlos@gmail.com";

        if(email.equals(emailMock) && passMock.equalsIgnoreCase(password))
            return true;
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
        obj.put("operacao", 1);
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
