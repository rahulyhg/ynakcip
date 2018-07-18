package com.prism.pickany247.Response;

import android.graphics.drawable.Drawable;

public class Country {

    String name = null;
    boolean selected = false;

    public Country( String name, boolean selected) {
        super();

        this.name = name;
        this.selected = selected;
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
