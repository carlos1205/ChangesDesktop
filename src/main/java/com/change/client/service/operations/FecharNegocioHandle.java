package com.change.client.service.operations;

import com.change.client.repository.chat.ChatDAO;
import com.change.model.Item;
import com.change.operations.EnumOperations;
import com.change.server.repository.ItemDAO;
import org.json.JSONObject;

import java.io.IOException;

public class FecharNegocioHandle extends IHandle{
    private static final EnumOperations operations = EnumOperations.FECHA_NEGOCIO_OUTRO_CLIENT;
    public FecharNegocioHandle(IHandle next){
        super(next);
    }

    public FecharNegocioHandle(){
        super();
    }

    @Override
    public void handle(JSONObject message) throws IOException {
        if(message.getInt("operacao") == operations.getNumber()){
            Item prod = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            boolean haveADeal = message.getJSONObject("data").getBoolean("flag_confirma");
            ChatDAO.getInstance().fecharNegocioReceive(haveADeal, prod, null);
        }else{
            super.handle(message);
        }
    }

    private Item getProduto(String code){
        return ItemDAO.getInstance().get(code);
    }
}
