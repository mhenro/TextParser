package com.mycompany.app.model.settings;

import java.io.Serializable;

/**
 * Created by mhenr on 07.02.2018.
 */
public class Page implements Serializable {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
