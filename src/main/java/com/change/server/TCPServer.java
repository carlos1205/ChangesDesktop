package com.change.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args){

        try{
            System.out.print("Digite a porta em que o servidor ira se conectar: ");
            int serverPort = Integer.valueOf(new Scanner(System.in).nextLine());
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
