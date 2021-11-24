package com.change.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args){

        try{
            int serverPort = 8765;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Servidor rodando na porta " + serverPort);
            while(true){
                Socket clientSocket = listenSocket.accept();
                ClientConnection c = new ClientConnection(clientSocket);
            }
        }catch(IOException e){
            System.out.println("Listen: " + e.getMessage());
        }
    }
}
