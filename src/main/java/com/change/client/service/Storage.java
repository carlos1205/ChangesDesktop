package com.change.client.service;

public class Storage {
    private static Storage instance;
    private String userId;

    private Storage(){}

    public static Storage getInstance(){
        if(null == instance)
            instance = new Storage();
        return instance;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }
}
