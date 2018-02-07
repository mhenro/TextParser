package com.mycompany.app.model.report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by berlogic on 2/7/18.
 */
public class RowModel {
    private int width;
    private int height;
    private List<ColumnModel> columns;

    public RowModel(final List<ColumnModel> columns){
        this.columns = columns;
    }

    public String renderRow() {
        String result = ReportModel.DATA_DELEMITER;
        int lineIndex = 0;
        boolean splitted;
        do {
            splitted = false;
            for (final ColumnModel column : columns) {
                List<String> lines = ReportModel.splitString(column.getData(), column.getWidth());
                final String data = lineIndex >= lines.size() ? "" : lines.get(lineIndex);
                result += ReportModel.SPACE + ReportModel.fillEmptySpace(data, column.getWidth()) + ReportModel.SPACE + ReportModel.DATA_DELEMITER;
                if (lines.size() > 1) {
                    splitted = true;
                }
            }
            ++lineIndex;
        } while(splitted);

        return result;
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

    public List<ColumnModel> getColumns() {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
}
