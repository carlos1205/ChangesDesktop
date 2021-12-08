package com.change.server.operations;

import com.change.model.Item;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.ItemDAO;
import com.change.server.service.ClientsManager;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteSP extends IOperation{
    private static final EnumOperations operations = EnumOperations.DELETAR_ITEM;
    public DeleteSP(IOperation next){
        super(next);
    }

    public DeleteSP(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(message.getJSONObject("data").getString("produto_servico_id"), client, messages);
        }else{
            super.handle(client, message);
        }
    }


    public void make(String id, ClientConnection client, List<String> messages) throws IOException {
        Item item = ItemDAO.getInstance().get(id);
        if(!ClientsManager.getInstance().getId(client.getIP()).equals(item.getOwner().getId())){
            messages.add("Operação Não permitida");
            client.send(makeResponse(true, messages));
            return;
        }

        if(ItemDAO.getInstance().delete(item)) {
            messages.add("Produto/Serviço Editado com sucesso");
            client.send(makeResponse(false, messages));
        }else{
            messages.add("Produto não existe.");
            client.send(makeResponse(true, messages));
        }
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
