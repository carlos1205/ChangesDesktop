package com.change.server.operations;

import com.change.model.Chat;
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

public class CloseConfirma extends IOperation {
    private static final EnumOperations operations = EnumOperations.FECHA_NEGOCIO_OUTRO_CLIENT;

    public CloseConfirma(IOperation next) {
        super(next);
    }

    public CloseConfirma() {
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> mensagem = new ArrayList<>();
        if (operations.getNumber() == message.getInt("operacao")) {
            Item item = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            Chat chat = ChatManager.getInstance().getChat(item.getOwner());
            User user = ClientsManager.getInstance().get(client).getUser();

            if (null != chat && !chat.isFechando()){
                boolean flag = message.getJSONObject("data").getBoolean("flag_confirma");
                ClientConnection destiny = null;
                if(!user.equals(chat.getUsuario()))
                    destiny = ClientsManager.getInstance().get(chat.getUsuario()).getConnect();
                else
                    destiny =  ClientsManager.getInstance().get(item.getOwner()).getConnect();

                chat.addFechado(flag);
                if(flag){
                    destiny.send(makeNotification(flag, item).toString());
                    mensagem.add("Fechando negocio");
                }else{
                    ChatManager.getInstance().removeChat(item.getOwner());
                    mensagem.add("Negocio Cancelado");
                }
                client.send(makeJSON(item, !flag, mensagem).toString());
            }else{
                mensagem.add("Negocio Cancelado");
                client.send(makeJSON(item, true, mensagem).toString());
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

    private JSONObject makeNotification(boolean flag, Item prod){
        JSONObject obj = new JSONObject();
        obj.put("operacao", EnumOperations.FECHA_NEGOCIO_OUTRO_CLIENT.getNumber());
        obj.put("data", new JSONObject()
                .put("produto_servico_id", prod.getCode())
                .put("flag_confirma", flag)
        );
        return obj;
    }

    private Item getProduto(String code) {
        return ItemDAO.getInstance().get(code);
    }
}