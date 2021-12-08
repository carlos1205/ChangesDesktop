package com.change.client.repository.chat;

import com.change.model.Item;

public class ChatDAO implements IChatDAO{
    @Override
    public String receiveMessage() {
        return "null";
    }

    @Override
    public void sendMessage(String message, Item product) {

    }

    @Override
    public void sendBroadCastMessage(String message) {

    }

    @Override
    public boolean fecharNegocio(boolean fechado, Item product) {
        return false;
    }

    @Override
    public boolean openChat(Item product) {
        return false;
    }
}
