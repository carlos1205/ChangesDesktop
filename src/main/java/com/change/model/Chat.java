package com.change.model;

import java.util.List;

public class Chat {
    private Item produto;
    private User usuario;
    private boolean fechando;
    private List<Boolean> fechado;

    public Chat(Item produto, User user){
        this.produto=produto;
        this.usuario=user;
    }

    public Item getProduto() {
        return produto;
    }

    public void setProduto(Item produto) {
        this.produto = produto;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public boolean isFechando() {
        return fechando;
    }

    public void setFechando(boolean fechando) {
        this.fechando = fechando;
    }

    public boolean isFechado() {
        if(fechado.size() != 2)
            return false;
        boolean res = true;
        for (boolean b: fechado){
            res = res && b;
        }
        return res;
    }

    public void addFechado(boolean fechado) {
        this.fechado.add(fechado);
    }
}
