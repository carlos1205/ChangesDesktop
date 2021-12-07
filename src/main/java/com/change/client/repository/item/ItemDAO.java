package com.change.client.repository.item;

import com.change.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements IItemDAO<Item>{
    private static ItemDAO instance;
    private final List<String> errors;
    private final List<String> success;

    public static ItemDAO getInstance(){
        if(null == instance)
            instance = new ItemDAO();

        return instance;
    }

    private ItemDAO(){
        errors = new ArrayList<>();
        success = new ArrayList<>();
    }

    @Override
    public String insert(Item item) {
        return "Cadastrado";
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<String> getErrors() {
        return null;
    }

    @Override
    public List<String> getSuccess() {
        return null;
    }
}
