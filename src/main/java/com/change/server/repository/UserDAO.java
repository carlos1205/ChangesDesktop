package com.change.server.repository;

import com.change.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;

    private List<User> users;

    public static UserDAO getInstance(){
        if(null == instance) instance = new UserDAO();

        return instance;
    }

    private UserDAO(){
        users = new ArrayList<>();
    }

    public boolean cadastrar(User user){
        users.add(user);
        return true;
    }

    private boolean validar(User user){

        return true;
    }
}
