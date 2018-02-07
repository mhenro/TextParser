package com.mycompany.app.model.report;

import com.mycompany.app.model.settings.Column;
import com.mycompany.app.model.settings.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by berlogic on 2/7/18.
 */
public class ReportModel {
    public static String PAGE_DELEMITER = "~";
    public static String DATA_DELEMITER = "|";
    public static String ROW_DELEMITER = "-";
    public static String RETURN_CHARACTER = "\n";
    public static String SPACE = " ";

    private List<List<String>> data;
    private int pageCount;
    private int pageWidth;
    private int pageHeight;
    private List<ColumnModel> columns;
    private RowModel header;


    public ReportModel(final Settings settings, final List<List<String>> reportData) {
        setData(reportData);
        updateReportModel(settings);
    }

    public List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        strings.add(printPageHeader());
        strings.add(printRawDelemiter());
        return strings;
    }

    public String printPageHeader() {
        return this.header.renderRow();
    }

    public String printRawDelemiter() {
        return IntStream.range(0, getPageWidth()).mapToObj(i -> ROW_DELEMITER).collect(Collectors.joining("")) + RETURN_CHARACTER;
    }

    private void updateReportModel(final Settings settings) {
        setPageWidth(settings.getPage().getWidth());
        setPageHeight(settings.getPage().getHeight());
        createHeader(settings);

        for (List<String> columnData : getData()) {
            //getColumns()
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

    public static String fillEmptySpace(final String str, final int maxLength) {
        if (str.length() < maxLength) {
            return str + IntStream.range(0, maxLength - str.length()).mapToObj(i -> SPACE).collect(Collectors.joining(""));
        }
        return str;
    }

    /* recursive method */
    public static List<String> splitString(final String str, final int maxLength) {
        List<String> result = new ArrayList<>();
        if (str.length() <= maxLength) {
            result.add(str);
            return result;
        }
        final String[] words = str.split("[-!@#$%&*() ]");
        Arrays.asList(words).stream().forEach(word -> {
            if (word.length() > maxLength) {
                String first = word.substring(0, maxLength);
                String second = word.substring(maxLength);
                result.add(first);
                result.addAll(splitString(second, maxLength));
            } else {
                result.add(word);
            }
        });

        //result.stream().forEach(System.out::println);

        return result;
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

    public List<ColumnModel> getColumns() {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        return columns;
    }

    private void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public RowModel getHeader() {
        return header;
    }

    public void setHeader(RowModel header) {
        this.header = header;
    }
}
