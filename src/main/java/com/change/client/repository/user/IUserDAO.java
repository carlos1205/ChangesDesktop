package com.change.client.repository.user;

import com.change.model.User;

import java.util.List;

public interface IUserDAO {
    boolean login(String email, String password);
    void logout();
    boolean cadastrar(String name, String email, String password);
    User getUser(String id);
    List<String> getErrors();
}
