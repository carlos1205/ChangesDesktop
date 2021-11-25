package com.change.client.config;

import com.change.client.config.annotations.Inject;
import com.change.client.controllers.CadastroController;
import com.change.client.controllers.HomeController;
import com.change.client.controllers.LoginController;
import com.change.client.repository.user.IUserDAO;
import com.change.client.repository.user.UserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private Map<Class, Class> classes;
    private List<Class> controllers;
    private static Config instance;

    public static Config getInstance(){
        if(null == instance)
            instance = new Config();
        return instance;
    }

    private Config(){
        classes = setClasses();
        controllers = setControllers();
    }

    private Map<Class, Class> setClasses(){
        Map<Class, Class> classes = new HashMap<>();
        classes.put(IUserDAO.class, UserDAO.class);
        classes.put(StageFactory.class, StageFactory.class);
        classes.put(Storage.class, Storage.class);
        return classes;
    }

    private List<Class> setControllers(){
        List<Class> controllers = new ArrayList<>();
        controllers.add(LoginController.class);
        controllers.add(HomeController.class);
        controllers.add(CadastroController.class);
        return controllers;
    }

    public void make(){
        try {
            for (Class clazz : controllers) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getAnnotation(Inject.class) == null)
                        continue;
                    setInstance(field);
                }
            }
        } catch (IllegalAccessException|NoSuchMethodException|InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setInstance(Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        field.setAccessible(true);
        field.set(null, classes.get(field.getType()).getMethod("getInstance").invoke(null));
    }
}
