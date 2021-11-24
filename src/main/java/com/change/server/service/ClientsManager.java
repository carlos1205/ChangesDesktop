package com.change.server.service;

import com.change.server.ClientConnection;

import java.util.HashMap;
import java.util.Map;

public class ClientsManager {
    private static ClientsManager instance;

    private Map<String, ClientConnection> clients;

    public static ClientsManager getInstance(){
        if(null == instance)
            instance = new ClientsManager();

        return instance;
    }

    private ClientsManager(){
        clients = new HashMap<>();
    }

    public void addClient(String uid, ClientConnection connection){
        this.clients.put(uid, connection);
    }

    public void removeClient(String uid){
        this.clients.remove(uid);
    }
}
