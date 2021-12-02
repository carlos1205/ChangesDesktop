package com.change.client.service;

import com.change.model.User;

public class Storage {
    private static Storage instance;
    private User userLogado;

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
}
