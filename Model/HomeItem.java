package com.prism.pickany247.Model;

public class HomeItem {

    private String id;
    private String image;
    private String category;
    private String image_type;
    private String position;
    private String title;

    public HomeItem(String id, String image, String category, String image_type, String position, String title) {
        this.id = id;
        this.image = image;
        this.category = category;
        this.image_type = image_type;
        this.position = position;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
