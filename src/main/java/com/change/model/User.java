package com.change.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public User(){}

    public static User make(String id, String name, String email, String password){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
