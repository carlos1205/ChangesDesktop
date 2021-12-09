package com.change.client.service.operations;

import com.change.client.repository.chat.ChatDAO;
import com.change.operations.EnumOperations;
import org.json.JSONObject;

import java.io.IOException;

public class ReceberMensagemHandle extends IHandle{
    private static final EnumOperations operations = EnumOperations.CHAT_RECEIVE_MESSAGE;
    public ReceberMensagemHandle(IHandle next){
        super(next);
    }

    public ReceberMensagemHandle(){
        super();
    }

    @Override
    public void handle(JSONObject message) throws IOException {
        if(message.getInt("operacao") == operations.getNumber()){
            String received = message.getJSONObject("data").getString("mensagem");
            ChatDAO.getInstance().receiveMessage(received);
        }
    }
}

