package com.change.client.service.operations;

import com.change.client.repository.chat.ChatDAO;
import com.change.model.Item;
import com.change.operations.EnumOperations;
import com.change.server.repository.ItemDAO;
import org.json.JSONObject;

import java.io.IOException;
public class OpenChatHandle extends IHandle{
    private static final EnumOperations operations = EnumOperations.CHAT_OPEN;
    public OpenChatHandle(IHandle next){
        super(next);
    }

    public OpenChatHandle(){
        super();
    }

    @Override
    public void handle(JSONObject message) throws IOException {
        if(message.getInt("operacao") == operations.getNumber()){
            Item prod = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            ChatDAO.getInstance().openChatReceive(prod);
        }
    }

    private Item getProduto(String code){
        return ItemDAO.getInstance().get(code);
    }
}
