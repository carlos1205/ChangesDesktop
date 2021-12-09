package com.change.server.service;

import com.change.model.Chat;
import com.change.model.EnumStatus;
import com.change.model.Item;
import com.change.model.User;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {
    private static ChatManager instance;

    private final Map<User, Chat> chats;

    public static ChatManager getInstance(){
        if(null == instance)
            instance = new ChatManager();

        return instance;
    }

    private ChatManager(){
        chats = new HashMap<>();
    }

    public void addChat(User user, Chat chat){
        this.chats.put(user, chat);
    }

    public void removeChat(User user){
        Chat chat = this.chats.get(user);
        if(chat.isFechado())
            chat.getProduto().setStatus(EnumStatus.FECHADO);
        this.chats.remove(user);
    }

    public Chat getChat(User user){
        return chats.get(user);
    }
}
