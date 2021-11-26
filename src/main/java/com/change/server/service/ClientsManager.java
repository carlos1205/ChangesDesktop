package com.change.server.service;

import java.util.HashMap;
import java.util.Map;

public class ClientsManager {
    private static ClientsManager instance;

    private final Map<String, String> clients;

    public static ClientsManager getInstance(){
        if(null == instance)
            instance = new ClientsManager();

        return instance;
    }

    private ClientsManager(){
        clients = new HashMap<>();
    }

    public void addClient(String ip, String uid){
        this.clients.put(ip, uid);
    }

    public void removeClient(String ip){
        this.clients.remove(ip);
    }
}
