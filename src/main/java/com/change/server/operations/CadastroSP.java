package com.change.server.operations;

import com.change.model.*;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.ItemDAO;
import com.change.server.repository.UserDAO;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroSP extends IOperation{
    private static final EnumOperations operations = EnumOperations.CADASTRO_ITEM;
    public CadastroSP(IOperation next){
        super(next);
    }

    public CadastroSP(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(parseJsonItem(message), client, messages);
        }else{
            super.handle(client, message);
        }
    }

    private Item parseJsonItem(JSONObject message) {
        JSONObject data = message.getJSONObject("data");
        User user = UserDAO.getInstance().getUserWithId(data.getString("usuario_id"));
        EnumCategoria category= findCategory(data.getString("categoria"));
        EnumServicoProduto sp = findSP(data.getString("flag_sp"));
        EnumVendaDoacaoTroca vdt = findVDT(data.getString("flag_vdt"));

        Item item = new Item(data.getString("titulo"),
                    data.getString("descricao"),
                    user,
                    category,
                    sp,
                    vdt
                );

        if(null != data.get("valor"))
            item.setPrice(data.getFloat("valor"));

        return item;
    }

    private EnumVendaDoacaoTroca findVDT(String flag_vdt) {
        for(EnumVendaDoacaoTroca vdt: EnumVendaDoacaoTroca.values()){
            if(vdt.getValue() == flag_vdt.charAt(0))
                return vdt;
        }
        return null;
    }

    private EnumServicoProduto findSP(String flag_sp) {
        for(EnumServicoProduto sp: EnumServicoProduto.values()){
            if(sp.getValue() == flag_sp.charAt(0))
                return sp;
        }
        return null;
    }

    private EnumCategoria findCategory(String categoria) {
        for(EnumCategoria cat: EnumCategoria.values()){
            if(cat.getName().equals(categoria))
                return cat;
        }
        return null;
    }

    public void make(Item item, ClientConnection client, List<String> messages) throws IOException {
        if(!IsValid(item)){
            messages.add("Nenhum campo pode ser vazio");
            client.send(makeResponse(true, messages));
            client.close();
            return;
        }

        if(null == ItemDAO.getInstance().get(item.getCode())) {
            String newCode = ItemDAO.getInstance().insert(item);
            messages.add("Produto/Serviço cadastrado com sucesso");
            client.send(makeResponseSuccess(false, messages, newCode));
        }else{
            messages.add("Item já cadastrado.");
            client.send(makeResponse(true, messages));
        }

        client.close();
    }

    private boolean IsValid(Item item) {
        if(null == item.getCategory())
            return false;
        if(null == item.getVdt())
            return false;
        if(null == item.getSp())
            return false;

        if("".equals(item.getDescription()))
            return false;
        if("".equals(item.getTitle()))
            return false;
        if(null == item.getOwner())
            return false;

        return true;
    }

    private String makeResponseSuccess(boolean error, List<String> messages, String code){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        obj.put("data", new JSONObject().put("produto_servico_id", code));
        return obj.toString();
    }

    private String makeResponse(boolean error, List<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        return obj.toString();
    }
}
