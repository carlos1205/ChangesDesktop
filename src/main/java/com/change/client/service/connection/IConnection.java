package com.change.client.service.connection;

import org.json.JSONObject;

import java.io.IOException;

public interface IConnection {
    void create(String host, int port);
    JSONObject send(String message);
    void close();
    public JSONObject receive() throws IOException;
}
