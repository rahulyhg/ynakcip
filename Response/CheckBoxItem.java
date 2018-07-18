package com.prism.pickany247.Response;

import android.graphics.drawable.Drawable;

public class CheckBoxItem {

    String id =null;
    String name = null;
    boolean selected = false;

    public CheckBoxItem(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
