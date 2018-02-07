package com.mycompany.app.model.report;

import com.mycompany.app.generator.ReportUtils;
import com.mycompany.app.model.settings.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by berlogic on 2/7/18.
 */
public class RowModel {
    private int width;
    private List<ColumnModel> columns;

    public RowModel(final List<ColumnModel> columns){
        this.columns = columns;
    }

    public static List<ColumnModel> createColumnModel(final List<String> columnData, final Settings settings) {
        List<ColumnModel> result = new ArrayList<>();
        int index = 0;
        for (final String data : columnData) {
            final ColumnModel column = new ColumnModel(data, settings.getColumns().get(index).getWidth());
            ++index;
            result.add(column);
        }

        return result;
    }

    public String renderRow() {
        String result = "";
        int lineIndex = 0;
        int maxLines = 0;
        do {
            result += ReportModel.DATA_DELEMITER;
            for (final ColumnModel column : columns) {
                List<String> lines = ReportUtils.splitString(column.getData(), column.getWidth());
                final String data = lineIndex >= lines.size() ? "" : lines.get(lineIndex);
                result += ReportModel.SPACE + ReportUtils.fillEmptySpace(data, column.getWidth()) + ReportModel.SPACE + ReportModel.DATA_DELEMITER;
                if (lines.size() > 1) {
                    maxLines = lines.size() > maxLines ? lines.size() : maxLines;
                }
            }
            result += ReportModel.RETURN_CHARACTER;
            ++lineIndex;
        } while(lineIndex < maxLines);
        //if (!result.isEmpty()) {
        //    result = result.substring(0, result.length() - 1);  //remove last RETURN_CHARACTER
        //}

        return result;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return renderRow().split(ReportModel.RETURN_CHARACTER).length;
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
