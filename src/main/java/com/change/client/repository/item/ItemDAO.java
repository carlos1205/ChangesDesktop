package com.change.client.repository.item;

import com.change.client.service.connection.ClientConnection;
import com.change.model.*;
import com.change.operations.EnumOperations;
import org.json.JSONArray;
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
        ClientConnection connection = ClientConnection.getInstance();
        JSONObject response = connection.send(new JSONObject().put("operacao", EnumOperations.LISTAGEM.getNumber()).toString());
        response.getJSONArray("mensagem").toList().forEach(message -> this.message.add(message.toString()));
        if(response.getBoolean("erro"))
            return null;

        return extractAll(response);
    }

    private List<Item> extractAll(JSONObject response) {
        List<Item> itens = new ArrayList<>();
        JSONArray array = response.getJSONArray("data");
        for(int i = 0; i < array.toList().size(); i++){
            JSONObject jsonObject = array.getJSONObject(i);
            User owner = new User();
            owner.setId(jsonObject.getString("usuario_id"));
            Item item = new Item(
                    jsonObject.getString("titulo"),
                    jsonObject.getString("descricao"),
                    owner,
                    getCategoryEnum(jsonObject.getString("categoria")),
                    getFlagSp(jsonObject.getString("flag_sp").charAt(0)),
                    getFlagVdt(jsonObject.getString("flag_vdt").charAt(0))
            );

            item.setCode(jsonObject.getString("produto_servico_id"));
            if(null != jsonObject.get("valor"))
                item.setPrice(jsonObject.getFloat("valor"));
            item.setStatus(getFlagStatus(jsonObject.getString("flag_status").charAt(0)));

            itens.add(item);
        }
        return itens;
    }

    private EnumStatus getFlagStatus(char flag_status) {
        for (EnumStatus status : EnumStatus.values()){
            if(status.getValue() == flag_status)
                return status;
        }
        return null;
    }

    private EnumVendaDoacaoTroca getFlagVdt(char flag_vdt) {
        for (EnumVendaDoacaoTroca vdt : EnumVendaDoacaoTroca.values()){
            if(vdt.getValue() == flag_vdt)
                return vdt;
        }
        return null;
    }

    private EnumCategoria getCategoryEnum(String categoria) {
        for (EnumCategoria cat : EnumCategoria.values()){
            if(cat.getName().equals(categoria))
                return cat;
        }
        return null;
    }

    private EnumServicoProduto getFlagSp(char flag){
        for (EnumServicoProduto sp : EnumServicoProduto.values()){
            if(sp.getValue() == flag)
                return sp;
        }
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
