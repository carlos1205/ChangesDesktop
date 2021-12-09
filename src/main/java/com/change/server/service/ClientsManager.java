package com.change.server.service;

import com.change.model.Client;
import com.change.model.User;
import com.change.server.ClientConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientsManager {
    private static ClientsManager instance;

    //private final Map<String, String> clients;
    private final List<Client> clients;

    public static ClientsManager getInstance(){
        if(null == instance)
            instance = new ClientsManager();

        return instance;
    }

    private ClientsManager(){
        clients = new ArrayList<>();
    }

    public void addClient(ClientConnection socket, User user){
        Client client = new Client(socket, user);
        clients.add(client);
    }

    public void removeClient(String ip){
        Client client = find(ip);
        this.clients.remove(client);
    }

    public String getId(String ip){
        Client client = find(ip);
        return client.getUser().getId();
    }

    public Client get(String ip){
        return find(ip);
    }

    public Client get(User user){
        return find(user);
    }

    public Client get(ClientConnection connection){
        return find(connection.getIP());
    }

    private Client find(String ip){
        return clients.stream().filter(client -> client.getIp().equals(ip)).findFirst().orElse(null);
    }

    private Client find(User user){
        return clients.stream().filter(client -> client.getUser().equals(user)).findFirst().orElse(null);
    }

    public List<Client> getActives(){
        return clients.stream().collect(Collectors.toList());
    }
}
