package com.change.client.service;

import com.change.model.Item;
import com.change.model.User;

public class Storage {
    private static Storage instance;
    private User userLogado;
    private Item item;

    private Storage(){}

    public static Storage getInstance(){
        if(null == instance)
            instance = new Storage();
        return instance;
    }

    public void setUser(User user){
        this.userLogado = user;
    }

    public User getUser(){
        return this.userLogado;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return this.item;
    }
}
