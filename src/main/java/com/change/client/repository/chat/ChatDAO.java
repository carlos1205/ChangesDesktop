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
    private FechaChatController page;

    public static ChatDAO getInstance(){
        if(null == instance)
            instance = new ChatDAO();
        return instance;
    }

    private ChatDAO(){
        message = new ArrayList<>();
    }

    @Override
    public void receiveMessage(String message) {
        chat.handleReceive(message);
    }

    @Override
    public void sendMessage(String message, Item product) {
        JSONObject data = new JSONObject()
                .put("produto_servico_id", product.getCode())
                .put("mensagem", message);

        ClientConnection.getInstance().sendWithoutResponse(new JSONObject().put("operacao", EnumOperations.CHAT_MESSAGE.getNumber()).put("data", data).toString());
    }

    @Override
    public void sendBroadCastMessage(String message) {

    }

    @Override
    public boolean fecharNegocio(boolean fechado, Item product, FechaChatController page) {
        if(null != page && null == this.page){
            page.handleConfirmacao("Negócio não fechado", false);
        }

        if(null != this.page && null != page){
            this.page = page;
            if(fechado){
                page.handleConfirmacao("O Acordo foi fechado", fechado);
            }else{
                page.handleConfirmacao("O Acordo não foi fechado", fechado);
            }
            JSONObject data = new JSONObject()
                    .put("produto_servico_id", product.getCode())
                    .put("flag_confirma", fechado);

            JSONObject response = ClientConnection.getInstance().send(new JSONObject().put("operacao", EnumOperations.FECHA_NEGOCIO_OUTRO_CLIENT.getNumber()).put("data", data).toString());
            response.getJSONArray("mensagem").toList().forEach(message -> this.message.add(message.toString()));
            return !response.getBoolean("erro");
        }

        if(null == this.page && null != page){
            JSONObject data = new JSONObject()
                    .put("produto_servico_id", product.getCode())
                    .put("flag_confirma", fechado);

            JSONObject response = ClientConnection.getInstance().send(new JSONObject().put("operacao", EnumOperations.CHAT_CLOSE.getNumber()).put("data", data).toString());
            response.getJSONArray("mensagem").toList().forEach(message -> this.message.add(message.toString()));
            return !response.getBoolean("erro");
        }

        return true;
    }

    @Override
    public void fecharNegocioReceive(boolean fechado, Item product, List<String> response) {
        if(null != this.page){
            page.handleConfirmacao(response.get(0), fechado);
        }else{
            Storage.getInstance().setItem(product);
            StageFactory.getInstance().destroy(EnumScenes.FECHA_CHAT);
            StageFactory.getInstance().changeScene(EnumScenes.FECHA_CHAT);

            if(!fechado){
                page.handleConfirmacao(response.get(0), fechado);
            }
        }
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
        return !(response.getBoolean("erro"));
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

    public void setPage(FechaChatController page){
        this.page = page;
    }
}
