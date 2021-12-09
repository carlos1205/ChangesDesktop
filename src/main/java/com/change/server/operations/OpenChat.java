package com.change.server.operations;

import com.change.model.Chat;
import com.change.model.Client;
import com.change.model.Item;
import com.change.model.User;
import com.change.operations.EnumOperations;
import com.change.server.ClientConnection;
import com.change.server.repository.ItemDAO;
import com.change.server.service.ChatManager;
import com.change.server.service.ClientsManager;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenChat extends IOperation {
    private static final EnumOperations operations = EnumOperations.CHAT;

    public OpenChat(IOperation next) {
        super(next);
    }

    public OpenChat() {
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> mensagem = new ArrayList<>();
        if (operations.getNumber() == message.getInt("operacao")) {
            Item item = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            Chat chat = ChatManager.getInstance().getChat(item.getOwner());
            Client ownerConnection = ClientsManager.getInstance().get(item.getOwner());
            if (null == chat && ownerConnection != null){//TODO: PEGAR O SOCKET DO USUARIO DONO DO PRODUTO
                User user = ClientsManager.getInstance().get(client.getIP()).getUser();
                ChatManager.getInstance().addChat(item.getOwner(), new Chat(item, user));
                mensagem.add("Sucesso");
                client.send(makeJSON(item, true, mensagem).toString());
                ownerConnection.getConnect().send(makeNotification(mensagem, item).toString());
            }else{
                mensagem.add("Usuário indisponivel");
                client.send(makeJSON(item, false, mensagem).toString());
            }
        } else {
            super.handle(client, message);
        }
    }

    private JSONObject makeJSON(Item prod, boolean erro, List<String> mensagem){
        JSONObject obj = new JSONObject();
        obj.put("operacao", operations.getNumber());
        obj.put("data", new JSONObject().put("produto_servico_id", prod.getCode()));
        obj.put("erro", erro);
        obj.put("mensagem", mensagem);
        return obj;
    }

    private JSONObject makeNotification(List<String> mensagem, Item prod){
        JSONObject obj = new JSONObject();
        obj.put("operacao", EnumOperations.CHAT_OPEN.getNumber());
        obj.put("data", new JSONObject().put("produto_servico_id", prod.getCode()));
        obj.put("erro", false);
        obj.put("mensagem", mensagem);
        return obj;
    }

    private Item getProduto(String code) {
        return ItemDAO.getInstance().get(code);
    }
}
