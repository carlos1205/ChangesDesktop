package com.change.model;

public class Chat {
    private Item produto;
    private User usuario;

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
}
