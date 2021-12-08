package com.change.server.repository;

import com.change.model.EnumStatus;
import com.change.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemDAO {
    private static ItemDAO instance;

    private final List<Item> itens;

    public static ItemDAO getInstance(){
        if(null == instance) instance = new ItemDAO();

        return instance;
    }

    private ItemDAO(){
        itens = new ArrayList<>();
    }

    public String insert(Item item){
        UUID uuid = UUID.randomUUID();
        item.setCode(uuid.toString());
        item.setStatus(EnumStatus.ABERTO);
        itens.add(item);
        return uuid.toString();
    }

    public boolean update(Item item){
        Item old = get(item.getCode());
        itens.remove(old);
        itens.add(item);
        return true;
    }

    public List<Item> getAll() {
        return itens.stream().filter(item -> item.getStatus() == EnumStatus.ABERTO).collect(Collectors.toList());
    }

    public boolean delete(String code){
        Item old = get(code);
        itens.remove(old);
        return true;
    }

    public Item get(String code){
        return itens.stream().filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }


}
