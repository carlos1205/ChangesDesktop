package com.change.client.service.operations;

import com.change.client.repository.chat.ChatDAO;
import com.change.model.Item;
import com.change.operations.EnumOperations;
import com.change.server.repository.ItemDAO;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CloseHandle extends IHandle{
    private static final EnumOperations operations = EnumOperations.FECHA_NEGOCIO_CLIENT;
    public CloseHandle(IHandle next){
        super(next);
    }

    public CloseHandle(){
        super();
    }

    @Override
    public void handle(JSONObject message) throws IOException {
        if(message.getInt("operacao") == operations.getNumber()){
            Item prod = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            boolean haveADeal = !message.getBoolean("erro");
            List<String> responses = new ArrayList<>();
            message.getJSONArray("mensagem").toList().forEach(msg -> responses.add(msg.toString()));
            ChatDAO.getInstance().fecharNegocioReceive(haveADeal, prod, responses);
        }
    }

    private Item getProduto(String code){
        return ItemDAO.getInstance().get(code);
    }
}