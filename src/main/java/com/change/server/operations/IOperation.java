package com.change.server.operations;

import com.change.server.ClientConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class IOperation {
    private IOperation next;

    public IOperation(IOperation next){
        this.next = next;
    }

    public IOperation(){
        this.next = null;
    }

    public void setNext(IOperation next){
        this.next = next;
    }

    public void handle(ClientConnection client, JSONObject message) throws IOException {
        if(null != next)
            next.handle(client, message);
        else
            client.send(makeResponse(true, Arrays.asList("Operacao invalida"), message.getInt("operacao")));
    }

    private String makeResponse(boolean error, List<String> messages, int operation){
        return new JSONObject()
                .put("operacao", operation)
                .put("erro", error)
                .put("mensagem", messages)
                .toString();
    }
}
