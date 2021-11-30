package com.change.client.repository.user;

import com.change.model.User;

import java.util.List;

public interface IUserDAO {
    boolean login(String email, String password);
    void logout();
    boolean create(String name, String email, String password);
    boolean update(String name, String email, String password);
    boolean delete();
    boolean forgetPass(String email);
    List<String> getErrors();
    List<String> getSuccess();
}
