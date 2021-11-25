package com.change.client.service;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    private static ClientConnection instance;

    private Socket socket;

    public static ClientConnection getInstance(){
        if(null == instance)
            instance = new ClientConnection("localhost", 25000);

        return instance;
    }

    private ClientConnection(String host, int port){
        this.socket = null;
        create(host, port);
    }

    public void create(String host, int port){
        try{
            this.socket = new Socket(host, port);
        }catch(UnknownHostException e){
            System.out.println("Sock: "+e.getMessage());
        }catch(EOFException e){
            System.out.println("EOF: "+e.getMessage());
        }catch(IOException e){
            System.out.println("IO: "+e.getMessage());
        }
    }

    public JSONObject send(String message){
        JSONObject response = null;
        try{
            PrintStream out = new PrintStream(this.socket.getOutputStream());
            out.println(message);
            System.out.println("Mensagem Enviada: " + message);

            response = receive();
            System.out.println("Mensagem Recebida: " + response);
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        return response;
    }

    public void close(){
        try{
            this.socket.close();
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        instance = null;
    }

    private JSONObject receive() throws IOException{
        String ln = null;

        BufferedReader read = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        char[] cbuf = new char[2048];
        read.read(cbuf);
        ln = String.valueOf(cbuf.clone());
        return new JSONObject(ln);
    }
}
