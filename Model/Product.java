package com.prism.pickany247.Model;

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
    public final String request_code;
    public final String cart_type;
    public final String eggless;
    public final String eggless_amt;
    public final String heart_shape;
    public final String heart_shape_amt;
    public final String flavour;
    public final String message;
    public final String delivery_time;

    public Product(String name, int quantity, double price, String thumbnail, String module, String user_id, String item_id, String cartId, String productId, String availability, String request_code, String cart_type, String eggless, String eggless_amt, String heart_shape, String heart_shape_amt, String flavour, String message, String delivery_time) {
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
        this.request_code = request_code;
        this.cart_type = cart_type;
        this.eggless = eggless;
        this.eggless_amt = eggless_amt;
        this.heart_shape = heart_shape;
        this.heart_shape_amt = heart_shape_amt;
        this.flavour = flavour;
        this.message = message;
        this.delivery_time = delivery_time;
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

    public String getRequest_code() {
        return request_code;
    }

    public String getCart_type() {
        return cart_type;
    }

    public String getEggless() {
        return eggless;
    }

    public String getEggless_amt() {
        return eggless_amt;
    }

    public String getHeart_shape() {
        return heart_shape;
    }

    public String getHeart_shape_amt() {
        return heart_shape_amt;
    }

    public String getFlavour() {
        return flavour;
    }

    public String getMessage() {
        return message;
    }

    public String getDelivery_time() {
        return delivery_time;
    }
}
