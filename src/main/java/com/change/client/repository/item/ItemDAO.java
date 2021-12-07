package com.change.client.repository.item;

import com.change.client.service.connection.ClientConnection;
import com.change.model.Item;
import com.change.operations.EnumOperations;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements IItemDAO<Item>{
    private static ItemDAO instance;
    private final List<String> message;

    public static ItemDAO getInstance(){
        if(null == instance)
            instance = new ItemDAO();

        return instance;
    }

    private ItemDAO(){
        message = new ArrayList<>();
    }

    @Override
    public String insert(Item item) {
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject response = connection.send(parseItemToJson(item, EnumOperations.CADASTRO_ITEM.getNumber()));
        return extractResponse(response) ? response.getJSONObject("data").getString("produto_servico_id"): null;
    }

    private boolean extractResponse(JSONObject response) {
        boolean res = Boolean.parseBoolean(response.get("erro").toString());
        response.getJSONArray("mensagem").toList().forEach(message -> this.message.add(message.toString()));
        return !res;
    }

    private String parseItemToJson(Item item, int op) {
        return new JSONObject()
                .put("operacao", op)
                .put("data", new JSONObject()
                        .put("usuario_id", item.getOwner().getId())
                        .put("categoria", item.getCategory().getName())
                        .put("flag_sp", String.valueOf(item.getSp().getValue()))
                        .put("descricao", item.getDescription())
                        .put("valor", item.getPrice() == 0 ? null : item.getPrice())
                        .put("titulo", item.getTitle())
                        .put("flag_vdt", String.valueOf(item.getVdt().getValue()))
                ).toString();
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<String> getMessage() {
        return null;
    }
}
