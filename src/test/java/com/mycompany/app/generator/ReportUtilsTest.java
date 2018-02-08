package com.mycompany.app.generator;

import com.mycompany.app.model.report.ColumnModel;
import com.mycompany.app.model.report.ReportModel;
import com.mycompany.app.model.report.RowModel;
import com.mycompany.app.model.settings.Column;
import com.mycompany.app.model.settings.Page;
import com.mycompany.app.model.settings.Settings;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mhenr on 08.02.2018.
 */
public class ReportUtilsTest {
    @Test
    public void generateReport() throws Exception {
        //TODO:
    }

    @Test
    public void createReportModel() throws Exception {
        final Settings settings = createSettings();
        final ReportModel report = ReportUtils.createReportModel(settings);
        List<RowModel> rows = report.getRows();
        Assert.assertEquals(5, rows.size());
        List<ColumnModel> columns = rows.get(0).getColumns();
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(7, columns.get(0).getWidth());
        Assert.assertEquals(8, columns.get(1).getWidth());
        Assert.assertEquals(9, columns.get(2).getWidth());
        Assert.assertEquals("1", columns.get(0).getData());
        Assert.assertEquals("25/11", columns.get(1).getData());
        Assert.assertEquals("Павлов Дмитрий", columns.get(2).getData());

        columns = rows.get(1).getColumns();
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(7, columns.get(0).getWidth());
        Assert.assertEquals(8, columns.get(1).getWidth());
        Assert.assertEquals(9, columns.get(2).getWidth());
        Assert.assertEquals("2", columns.get(0).getData());
        Assert.assertEquals("26/11", columns.get(1).getData());
        Assert.assertEquals("Павлов Константин", columns.get(2).getData());

        columns = rows.get(2).getColumns();
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(7, columns.get(0).getWidth());
        Assert.assertEquals(8, columns.get(1).getWidth());
        Assert.assertEquals(9, columns.get(2).getWidth());
        Assert.assertEquals("3", columns.get(0).getData());
        Assert.assertEquals("27/11", columns.get(1).getData());
        Assert.assertEquals("Н/Д", columns.get(2).getData());

        columns = rows.get(3).getColumns();
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(7, columns.get(0).getWidth());
        Assert.assertEquals(8, columns.get(1).getWidth());
        Assert.assertEquals(9, columns.get(2).getWidth());
        Assert.assertEquals("4", columns.get(0).getData());
        Assert.assertEquals("28/11", columns.get(1).getData());
        Assert.assertEquals("Ким Чен Ир", columns.get(2).getData());

        columns = rows.get(4).getColumns();
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(7, columns.get(0).getWidth());
        Assert.assertEquals(8, columns.get(1).getWidth());
        Assert.assertEquals(9, columns.get(2).getWidth());
        Assert.assertEquals("5", columns.get(0).getData());
        Assert.assertEquals("29/11/2009", columns.get(1).getData());
        Assert.assertEquals("Юлианна-Оксана Сухово-Кобылина", columns.get(2).getData());
    }

    @Test
    public void fillEmptySpace() throws Exception {
        final String resultEmpty = ReportUtils.fillEmptySpace("", 8);
        Assert.assertEquals("        ", resultEmpty);
        final String resultHalf = ReportUtils.fillEmptySpace("abcd", 8);
        Assert.assertEquals("abcd    ", resultHalf);
        final String resultFull = ReportUtils.fillEmptySpace("abcdefgh", 8);
        Assert.assertEquals("abcdefgh", resultFull);
    }

    @Test
    public void splitString() throws Exception {
        final List<String> emptyString = ReportUtils.splitString("", 8);
        Assert.assertEquals(1, emptyString.size());
        Assert.assertEquals("", emptyString.get(0));
        final List<String> halfString = ReportUtils.splitString("abcd", 8);
        Assert.assertEquals(1, halfString.size());
        Assert.assertEquals("abcd", halfString.get(0));
        final List<String> fullString = ReportUtils.splitString("abcdefgh", 8);
        Assert.assertEquals(1, fullString.size());
        Assert.assertEquals("abcdefgh", fullString.get(0));
        final List<String> largeString = ReportUtils.splitString("abcdefghabcd", 8);
        Assert.assertEquals(2, largeString.size());
        Assert.assertEquals("abcdefgh", largeString.get(0));
        Assert.assertEquals("abcd", largeString.get(1));
        final List<String> largeStringWithSpaces = ReportUtils.splitString("abc de fghabc d", 8);
        Assert.assertEquals(2, largeStringWithSpaces.size());
        Assert.assertEquals("abc de ", largeStringWithSpaces.get(0));
        Assert.assertEquals("fghabc d", largeStringWithSpaces.get(1));
        final List<String> largeStringWithDelemiter = ReportUtils.splitString("abc-de/fghabc^d", 8);
        Assert.assertEquals(2, largeStringWithDelemiter.size());
        Assert.assertEquals("abc-de/", largeStringWithDelemiter.get(0));
        Assert.assertEquals("fghabc^d", largeStringWithDelemiter.get(1));
    }

    private Settings createSettings() {
        final Settings settings = new Settings();
        settings.setPage(createPage());
        settings.setColumns(createColumns(3));
        settings.setDataPath("src/test/resources/data.tsv");
        return settings;
    }

    private Page createPage() {
        final Page page = new Page();
        page.setWidth(32);
        page.setHeight(12);
        return page;
    }

    private List<Column> createColumns(final int count) {
        return IntStream.range(0, count).mapToObj(n -> {
            final Column column = new Column();
            column.setWidth(7 + n);
            column.setTitle("Title #" + n);
            return column;
        }).collect(Collectors.toList());
    }
}
