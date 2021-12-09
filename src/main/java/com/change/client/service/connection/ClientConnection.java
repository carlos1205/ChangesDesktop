package com.change.client.service.connection;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements IConnection{
    private static ClientConnection instance;

    private static String host;
    private static int port;

    private Socket socket;

    public static ClientConnection getInstance(){
        if(null == instance)
            instance = new ClientConnection(host, port);
        return instance;
    }

    private ClientConnection(String host, int port){
        this.socket = null;
        create(host, port);
        ChatConnection.getInstance().start();
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
        }catch (IOException | InterruptedException e){
            System.out.println("IO: "+e.getMessage());
        }
        return response;
    }

    public void close(){
        try{
            this.socket.close();
            ChatConnection.getInstance().interrupt();
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
        instance = null;
    }

    public synchronized JSONObject receive() throws IOException, InterruptedException {
        String ln;
        BufferedReader read = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        char[] cbuf = new char[2048];
        read.read(cbuf);
        ln = String.valueOf(cbuf.clone());
        return new JSONObject(ln);
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
