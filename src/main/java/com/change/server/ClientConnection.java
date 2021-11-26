package com.change.server;

import com.change.server.service.OperationsFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread{
    private final Socket clientSocket;

    public ClientConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
        System.out.println("Client Connectado: "+getIP());
        this.start();
    }

    public void run(){
        try{
            while (!clientSocket.isClosed()) {
                JSONObject data = receive();
                System.out.println("Received:> " + data);

                OperationsFactory.getInstance().getOperations().handle(this, data);
            }
        }catch (JSONException e){
            System.out.println("Cliente desconectado");
        }catch (EOFException e){
            System.out.println("EOF: "+e.getMessage());
        }catch (IOException e){
            System.out.println("IO: "+e.getMessage());
        }
    }

    public JSONObject receive() throws IOException, JSONException {
        String ln;
        BufferedReader read = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        char[] cbuf = new char[2048];
        read.read(cbuf);
        ln = String.valueOf(cbuf.clone());
        return new JSONObject(ln);
    }

    public void send(String response) throws IOException, JSONException {
        PrintStream out = new PrintStream(this.clientSocket.getOutputStream());
        out.println(response);
        System.out.println("Send:> " + response);
    }

    public void close(){
        try {
            if(clientSocket.isConnected())
                clientSocket.close();
            System.out.println("Client Desconectado");
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIP(){
        return clientSocket.getInetAddress().getHostAddress();
    }
}
