package com.change.client.repository.chat;

import com.change.model.Item;

public interface IChatDAO {
    String receiveMessage();
    void sendMessage(String message, Item product);
    void sendBroadCastMessage(String message);
    boolean fecharNegocio(boolean fechado, Item product);
    boolean openChat(Item product);
}
