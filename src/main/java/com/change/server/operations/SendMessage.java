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

public class SendMessage extends IOperation {
    private static final EnumOperations operations = EnumOperations.CHAT_MESSAGE;

    public SendMessage(IOperation next) {
        super(next);
    }

    public SendMessage() {
        super();
    }

    @Override
    public void handle(ClientConnection client, JSONObject message) throws IOException {
        List<String> mensagem = new ArrayList<>();
        if (operations.getNumber() == message.getInt("operacao")) {
            Item item = getProduto(message.getJSONObject("data").getString("produto_servico_id"));
            Chat chat = ChatManager.getInstance().getChat(item.getOwner());
            User user = ClientsManager.getInstance().get(client).getUser();

            if (null != chat){//TODO: PEGAR O SOCKET DO USUARIO DONO DO PRODUTO
                String msg = message.getJSONObject("data").getString("mensagem");
                ClientConnection destiny = null;
                if(!user.equals(chat.getUsuario()))
                    destiny = ClientsManager.getInstance().get(chat.getUsuario()).getConnect();
                else
                    destiny =  ClientsManager.getInstance().get(item.getOwner()).getConnect();

                destiny.send(makeNotification(msg, item, user).toString());
            }
        } else {
            super.handle(client, message);
        }
    }

    private JSONObject makeNotification(String mensagem, Item prod, User user){
        JSONObject obj = new JSONObject();
        obj.put("operacao", EnumOperations.CHAT_RECEIVE_MESSAGE.getNumber());
        obj.put("data", new JSONObject()
                .put("produto_servico_id", prod.getCode())
                .put("usuario_id", user.getId())
                .put("nome_usuario", user.getName())
                .put("mensagem", mensagem)
        );
        return obj;
    }

    private Item getProduto(String code) {
        return ItemDAO.getInstance().get(code);
    }
}
