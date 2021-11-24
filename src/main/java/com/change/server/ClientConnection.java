package com.change.server;

import com.change.server.service.OperationsFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread{
    private Socket clientSocket;

    public ClientConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
        this.start();
    }

    public void run(){
        try{
            while (true) {
                JSONObject data = receive();
                System.out.println("Received:> " + data);

                String response = OperationsFactory.getInstance().getOperations().handle(data);

                PrintStream out = new PrintStream(this.clientSocket.getOutputStream());
                out.println(response);
                System.out.println("Send:> " + response);
            }
        }catch (JSONException e){
            System.out.println("Cliente desconectado");
        }catch (EOFException e){
            System.out.println("EOF: "+e.getMessage());
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

    private JSONObject receive() throws IOException, JSONException {
        String ln = null;
        BufferedReader read = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        char[] cbuf = new char[2048];
        read.read(cbuf);
        ln = String.valueOf(cbuf.clone());
        return new JSONObject(ln);
    }
}
