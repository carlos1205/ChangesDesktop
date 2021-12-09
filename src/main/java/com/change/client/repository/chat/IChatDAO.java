package com.change.client.repository.chat;

import com.change.client.controllers.ChatController;
import com.change.client.controllers.FechaChatController;
import com.change.model.Item;

import java.util.List;

public interface IChatDAO {
    void receiveMessage();
    void sendMessage(String message, Item product);
    void sendBroadCastMessage(String message);
    void openChatReceive(Item product);
    void fecharNegocio(boolean fechado, Item product, FechaChatController page);
    boolean openChat(Item product);
    List<String> getMessage();
    void setChat(ChatController chat);
}
