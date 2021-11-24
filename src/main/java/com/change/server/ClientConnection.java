package com.change.server;

import com.change.server.service.OperationsFactory;
import com.google.gson.Gson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    public ClientConnection(Socket clientSocket){
        try{
            this.clientSocket = clientSocket;
            this.in = new DataInputStream(this.clientSocket.getInputStream());
            this.out = new DataOutputStream(this.clientSocket.getOutputStream());
            this.start();
        }catch (IOException e){
            System.out.println("Connection: "+e.getMessage());
        }
    }

    public void run(){
        try{
            while (true) {
                String data = this.in.readUTF();
                if(null == data)
                    continue;

                System.out.println("Mensagem Recebida: " + data);
                String response = OperationsFactory.getInstance().getOperations().handle(parser(data));

                System.out.println("Mensagem Enviada: " + response);
                this.out.writeUTF(response);
            }
        }catch (EOFException e){
            System.out.println("A aplicação cliente se desconectou");
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }finally {
            try {
                this.clientSocket.close();
            }catch (IOException e){
                System.out.println("IO: "+e.getMessage());
            }
        }
    }

    private JsonObject parser(String data){
        JsonReader reader = Json.createReader(new StringReader(data));
        return reader.readObject();
    }
}
