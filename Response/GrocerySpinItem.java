package com.prism.pickany247.Response;

public class GrocerySpinItem {

    String itemId;
    String capacity;
    String price;

    public GrocerySpinItem(String itemId, String capacity, String price) {
        this.itemId = itemId;
        this.capacity = capacity;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
