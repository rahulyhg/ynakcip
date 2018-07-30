package com.prism.pickany247.Response;

public class Product {
    public final String name;
    public final int quantity;
    public final double price;
    public final String thumbnail;
    public final String module;
    public final String user_id;
    public final String item_id;
    public final String cartId;
    public final String productId;
    public final String availability;

    public Product(String name, int quantity, double price, String thumbnail, String module, String user_id, String item_id, String cartId, String productId, String availability) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.thumbnail = thumbnail;
        this.module = module;
        this.user_id = user_id;
        this.item_id = item_id;
        this.cartId = cartId;
        this.productId = productId;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getModule() {
        return module;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getCartId() {
        return cartId;
    }

    public String getProductId() {
        return productId;
    }

    public String getAvailability() {
        return availability;
    }
}
