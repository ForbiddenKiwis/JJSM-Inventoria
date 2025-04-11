package com.example.jjsminventoria.Builder;

import model.Products;

public class ProductBuilder {
    private String id;
    private String name;
    private String desc;
    private String img;
    private int qty;
    private double weight;

    public ProductBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public ProductBuilder setImg(String img) {
        this.img = img;
        return this;
    }

    public ProductBuilder setQty(int qty) {
        this.qty = qty;
        return this;
    }

    public ProductBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Products build() {
        Products product = new Products();
        product.setId(id);           // if null, Firebase auto-generates it later
        product.setName(name);
        product.setDesc(desc);
        product.setImg(img);
        product.setQty(qty);
        product.setWeight(weight);
        return product;
    }
}
