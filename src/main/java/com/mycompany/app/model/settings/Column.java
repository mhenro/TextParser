package com.mycompany.app.model.settings;

import java.io.Serializable;

/**
 * Created by mhenr on 07.02.2018.
 */
public class Column implements Serializable {
    private String title;
    private int width;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
