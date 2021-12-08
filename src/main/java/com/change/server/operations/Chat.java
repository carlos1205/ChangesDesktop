package com.change.server.operations;

import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chat extends IOperation{
    private static final EnumOperations operations = EnumOperations.CHAT;
    public Chat(IOperation next){
        super(next);
    }

    public Chat(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){

        }else{
            super.handle(client, message);
        }
    }
}
