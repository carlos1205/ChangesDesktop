package com.change.client.repository.chat;

import com.change.client.EnumScenes;
import com.change.client.controllers.ChatController;
import com.change.client.controllers.FechaChatController;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.client.service.connection.ClientConnection;
import com.change.model.Item;
import com.change.model.User;
import com.change.operations.EnumOperations;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements IChatDAO{
    private static ChatDAO instance;
    private final List<String> message;

    private ChatController chat;

    public static ChatDAO getInstance(){
        if(null == instance)
            instance = new ChatDAO();
        return instance;
    }

    private ChatDAO(){
        message = new ArrayList<>();
    }

    @Override
    public void receiveMessage() {
        String message = "mensagem recebida";
        chat.handleReceive(message);
    }

    @Override
    public void sendMessage(String message, Item product) {
        System.out.println("Mensagem Enviada");
        this.receiveMessage();
    }

    @Override
    public void sendBroadCastMessage(String message) {

    }

    @Override
    public void fecharNegocio(boolean fechado, Item product, FechaChatController page) {
        if(null != page)
            page.handleConfirmacao("Negócio não fechado", false);
    }

    @Override
    public void openChatReceive(Item product) {
        User user = Storage.getInstance().getUser();
        if(user.getId().equals(product.getOwner().getId())){
            Storage.getInstance().setItem(product);
            StageFactory.getInstance().destroy(EnumScenes.CHAT);
            StageFactory.getInstance().changeScene(EnumScenes.CHAT);
        }
    }

    @Override
    public boolean openChat(Item product) {
        ClientConnection connection = ClientConnection.getInstance();

        JSONObject response = connection.send(
                new JSONObject().put("operacao", EnumOperations.CHAT.getNumber())
                .put("data", new JSONObject().put("produto_servico_id", product.getCode()))
                .toString()
        );

        response.getJSONArray("mensagem").toList().forEach(message -> this.message.add(message.toString()));
        //return !(response.getBoolean("erro"));
        return true;
    }

    @Override
    public List<String> getMessage() {
        List<String> msg = new ArrayList<>(message);
        message.clear();
        return msg;
    }

    public void setChat(ChatController chat){
        this.chat = chat;
    }
}
