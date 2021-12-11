package com.change.client.service.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements IConnection{
    private static ClientConnection instance;

    private static String host;
    private static int port;

    private Socket socket;
    private ChatConnection chat;

    public static ClientConnection getInstance(){
        if(null == instance)
            instance = new ClientConnection(host, port);
        return instance;
    }

    private ClientConnection(String host, int port){
        this.socket = null;
        create(host, port);

        chat = new ChatConnection();
        chat.start();
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

    public void send(String message){
        try{
            PrintStream out = new PrintStream(this.socket.getOutputStream());
            out.println(message);
            System.out.println("Mensagem Enviada: " + message);
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
    }

    public void close(){
        try{
            chat.close();
            if(this.socket.isConnected())
                this.socket.close();
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        instance = null;
    }

    public synchronized JSONObject receive() throws IOException, InterruptedException, JSONException {
        String ln;
        BufferedReader read = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        char[] cbuf = new char[2048];
        read.read(cbuf);
        ln = String.valueOf(cbuf.clone());
        JSONObject obj = null;

        try{
            obj = new JSONObject(ln);
            System.out.println("Mensagem Recebida: " + obj.toString());
        }catch (JSONException e){
            chat.close();
        }

        return obj;
    }

    public void sendWithoutResponse(String message){
        PrintStream out = null;
        try {
            out = new PrintStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(message);
        System.out.println("Mensagem Enviada: " + message);
    }

    public static void setHost(String host) {
        ClientConnection.host = host;
    }

    public static void setPort(int port) {
        ClientConnection.port = port;
    }
}
