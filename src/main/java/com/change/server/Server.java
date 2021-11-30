package com.change.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private static Server instance;

    private int port;
    private Server(int port){
        this.port = port;
        this.start();
    }

    public static Server getInstance(int port){
        if(null == instance)
            instance = new Server(port);
        return instance;
    }

    public void run() {
        ServerSocket listenSocket = null;
        try {
            listenSocket = new ServerSocket(this.port);
            while(true){
                Socket clientSocket = listenSocket.accept();
                ClientConnection c = new ClientConnection(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        this.interrupt();
        instance = null;
    }

    public int getPort(){
        return port;
    }
}
