package com.change.client.service.connection;

import org.json.JSONObject;

public interface IConnection {
    void create(String host, int port);
    JSONObject send(String message);
    void close();
}
