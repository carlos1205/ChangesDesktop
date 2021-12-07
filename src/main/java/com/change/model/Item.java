package com.change.model;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    private String code;
    private String title;
    private String description;
    private float price;
    private User owner;
    private EnumCategoria category;
    private EnumServicoProduto sp;
    private EnumVendaDoacaoTroca vdt;
    private EnumStatus status;

    public Item(String title, String description, User owner, EnumCategoria category, EnumServicoProduto sp, EnumVendaDoacaoTroca vdt){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.category = category;
        this.sp = sp;
        this.vdt = vdt;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public EnumCategoria getCategory() {
        return category;
    }

    public void setCategory(EnumCategoria category) {
        this.category = category;
    }

    public EnumServicoProduto getSp() {
        return sp;
    }

    public void setSp(EnumServicoProduto sp) {
        this.sp = sp;
    }

    public EnumVendaDoacaoTroca getVdt() {
        return vdt;
    }

    public void setVdt(EnumVendaDoacaoTroca vdt) {
        this.vdt = vdt;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return code.equals(item.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
