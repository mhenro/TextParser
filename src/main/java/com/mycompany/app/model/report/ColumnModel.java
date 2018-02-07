package com.mycompany.app.model.report;

import com.mycompany.app.model.settings.Column;
import com.mycompany.app.model.settings.Settings;

import java.util.List;

/**
 * Created by berlogic on 2/7/18.
 */
public class ColumnModel {
    private int width;
    private int height;
    private String data;
    private List<String> rows;

    public ColumnModel(final Column column) {
        this.width = column.getWidth();
        this.data = column.getTitle();
    }

    public ColumnModel(final String data, final int width) {
        this.data = data;
        this.width = width;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }
}
