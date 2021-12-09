package com.change.server.operations;

import com.change.model.*;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.ItemDAO;
import com.change.server.repository.UserDAO;
import com.change.server.service.ClientsManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListagemSP extends IOperation {
    private static final EnumOperations operations = EnumOperations.LISTAGEM;

    public ListagemSP(IOperation next){
        super(next);
    }

    public ListagemSP(){
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> messages = new ArrayList<>();
        if(operations.getNumber() == message.getInt("operacao")){
            make(client, messages);
        }else{
            super.handle(client, message);
        }
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

    public void make(ClientConnection client, List<String> messages) throws IOException {
        List<User> usersOnline = getUserOnline();
        List<Item> itens = ItemDAO.getInstance().getAll().stream()
                .filter(item -> usersOnline.contains(item.getOwner()))
                .collect(Collectors.toList());
        if (null == itens){
            messages.add("Internal error");
            client.send(makeResponse(true, messages));
        }else{
            messages.add("Sucesso");
            JSONArray array = parseArray(itens);
            client.send(makeResponseSuccess(false, messages, array));
        }
    }

    private JSONArray parseArray(List<Item> itens) {
        JSONArray data = new JSONArray();
        itens.forEach(item -> {
            data.put(new JSONObject()
                    .put("produto_servico_id", item.getCode())
                    .put("usuario_id", item.getOwner().getId())
                    .put("categoria", item.getCategory().getName())
                    .put("descricao", item.getDescription())
                    .put("valor", item.getPrice() != 0 ? item.getPrice() : null)
                    .put("titulo", item.getTitle())
                    .put("flag_sp", String.valueOf(item.getSp().getValue()))
                    .put("flag_vdt", String.valueOf(item.getVdt().getValue()))
                    .put("flag_status", String.valueOf(item.getStatus().getValue()))
            );
        });
        return data;
    }

    private List<User> getUserOnline() {
        List<Client> clients = ClientsManager.getInstance().getActives();
        return clients.stream().map(client -> client.getUser()).collect(Collectors.toList());
    }

    private String makeResponseSuccess(boolean error, List<String> messages, JSONArray data){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("erro", error);
        obj.put("mensagem", messages);
        obj.put("data", data);
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
