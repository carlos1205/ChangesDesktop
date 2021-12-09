package com.change.client.config;

import com.change.client.config.annotations.Inject;
import com.change.client.controllers.*;
import com.change.client.repository.chat.ChatDAO;
import com.change.client.repository.chat.IChatDAO;
import com.change.client.repository.item.IItemDAO;
import com.change.client.repository.item.ItemDAO;
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
    private final Map<Class, Class> injects;
    private final List<Class> classes;
    private static Config instance;

    public static Config getInstance(){
        if(null == instance)
            instance = new Config();
        return instance;
    }

    private Config(){
        injects = setMap();
        classes = setClasses();
    }

    private Map<Class, Class> setMap(){
        Map<Class, Class> injects = new HashMap<>();
        injects.put(IUserDAO.class, UserDAO.class);
        injects.put(StageFactory.class, StageFactory.class);
        injects.put(Storage.class, Storage.class);
        injects.put(IMenuHandle.class, MenuController.class);
        injects.put(IItemDAO.class, ItemDAO.class);
        injects.put(IChatDAO.class, ChatDAO.class);
        return injects;
    }

    private List<Class> setClasses(){
        List<Class> classes = new ArrayList<>();
        // Controllers
        classes.add(LoginController.class);
        classes.add(HomeController.class);
        classes.add(CadastroController.class);
        classes.add(EditUserController.class);
        classes.add(ExcluirUserController.class);
        classes.add(ForgetPassSendController.class);
        classes.add(CadastroSPController.class);
        classes.add(ListagemController.class);
        classes.add(MyItensController.class);
        classes.add(EditItemController.class);
        classes.add(ExcluirItemController.class);
        classes.add(ChatController.class);
        classes.add(FechaChatController.class);
        return classes;
    }

    public void make(){
        try {
            for (Class clazz : classes) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getAnnotation(Inject.class) == null)
                        continue;
                    setInstance(field);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("IAE: :" +e.getMessage());
        } catch(NoSuchMethodException e){
            System.out.println("NSME: :" +e.getMessage());
        } catch (InvocationTargetException e){
            System.out.println("ITE: :" +e.getMessage());
        }
    }

    private void setInstance(Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        field.setAccessible(true);
        Object obj = injects.get(field.getType()).getMethod("getInstance").invoke(null);
        field.set(null, obj);
    }
}
