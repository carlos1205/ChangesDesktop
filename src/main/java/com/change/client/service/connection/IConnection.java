package com.change.client.service.connection;

import org.json.JSONObject;

import java.io.IOException;

public interface IConnection {
    void create(String host, int port);
    void send(String message);
    void close();
    JSONObject receive() throws IOException, InterruptedException;
    void sendWithoutResponse(String message);
}
