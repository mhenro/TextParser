package com.mycompany.app.model.report;

import com.mycompany.app.model.settings.Column;
import com.mycompany.app.model.settings.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by berlogic on 2/7/18.
 */
public class ReportModel {
    public static String PAGE_DELEMITER = "~";
    public static String DATA_DELEMITER = "|";
    public static String ROW_DELEMITER = "-";
    public static String RETURN_CHARACTER = "\r\n";
    public static String SPACE = " ";

    private List<List<String>> data;
    private int pageCount;
    private int pageWidth;
    private int pageHeight;
    private List<RowModel> rows;
    private RowModel header;


    public ReportModel(final Settings settings, final List<List<String>> reportData) {
        setData(reportData);
        updateReportModel(settings);
    }

    public List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        List<String> tempBuffer = new ArrayList<>();
        tempBuffer.add(printPageHeader());
        tempBuffer.add(printRowDelemiter());
        long pageOffset;
        for (final RowModel row : getRows()) {
            pageOffset = tempBuffer.stream().flatMap(str -> Arrays.asList(str.split(RETURN_CHARACTER)).stream()).count();
            if (pageOffset + row.getHeight() > pageHeight) {
                strings.addAll(tempBuffer);
                strings.add(PAGE_DELEMITER + RETURN_CHARACTER);
                tempBuffer.clear();
                tempBuffer.add(printPageHeader());
                tempBuffer.add(printRowDelemiter());
                tempBuffer.add(row.renderRow());
                tempBuffer.add(printRowDelemiter());
            } else {
                tempBuffer.add(row.renderRow());
                pageOffset = tempBuffer.stream().flatMap(str -> Arrays.asList(str.split(RETURN_CHARACTER)).stream()).count();
                if (pageOffset < pageHeight) {
                    tempBuffer.add(printRowDelemiter());
                }
            }
        }
        strings.addAll(tempBuffer);
        return strings;
    }

    private String printPageHeader() {
        return this.header.renderRow();
    }

    private String printRowDelemiter() {
        return IntStream.range(0, getPageWidth()).mapToObj(i -> ROW_DELEMITER).collect(Collectors.joining("")) + RETURN_CHARACTER;
    }

    private void updateReportModel(final Settings settings) {
        setPageWidth(settings.getPage().getWidth());
        setPageHeight(settings.getPage().getHeight());
        createHeader(settings);

        for (List<String> columnData : getData()) {
            final RowModel row = new RowModel(RowModel.createColumnModel(columnData, settings));
            getRows().add(row);
        }
    }

    private void createHeader(final Settings settings) {
        List<ColumnModel> columns = new ArrayList<>();
        for (final Column column : settings.getColumns()) {
            final ColumnModel model = new ColumnModel(column);
            columns.add(model);
        }
        this.header = new RowModel(columns);
        this.header.setWidth(getPageWidth());
    }

    public List<List<String>> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    private void setData(List<List<String>> data) {
        this.data = data;
    }

    public int getPageCount() {
        return pageCount;
    }

    private void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    private void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    private void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public List<RowModel> getRows() {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        return rows;
    }

    public void setRows(List<RowModel> rows) {
        this.rows = rows;
    }

    public RowModel getHeader() {
        return header;
    }

    public void setHeader(RowModel header) {
        this.header = header;
    }
}
