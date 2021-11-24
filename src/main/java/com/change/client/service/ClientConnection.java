package com.change.client.service;

import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    private static ClientConnection instance;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public static ClientConnection getInstance(){
        if(null == instance)
            instance = new ClientConnection("localhost", 8765);

        return instance;
    }

    private ClientConnection(String host, int port){
        this.socket = null;
        create(host, port);
    }

    public void create(String host, int port){
        try{
            this.socket = new Socket(host, port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        }catch(UnknownHostException e){
            System.out.println("Sock: "+e.getMessage());
        }catch(EOFException e){
            System.out.println("EOF: "+e.getMessage());
        }catch(IOException e){
            System.out.println("IO: "+e.getMessage());
        }
    }

    public JSONObject send(String message){
        String response = null;
        try{
            this.out.writeUTF(message);
            System.out.println("Mensagem Enviada: " + message);

            response = this.in.readUTF();
            System.out.println("Mensagem Recebida: " + response);
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        return parser(response);
    }

    public void close(){
        try{
            this.socket.close();
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        instance = null;
    }

    private JSONObject parser(String data){
        JSONObject json = new JSONObject(data);
        return json;
    }
}
