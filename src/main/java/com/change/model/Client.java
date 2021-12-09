package com.change.model;

import com.change.server.ClientConnection;

public class Client {
    private ClientConnection connect;
    private User user;

    public Client(ClientConnection connect, User user){
        this.connect = connect;
        this.user = user;
    }

    public ClientConnection getConnect() {
        return connect;
    }

    public void setConnect(ClientConnection connect) {
        this.connect = connect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp(){
        return connect.getIP();
    }
}
