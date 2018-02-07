package com.mycompany.app.model.report;

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
public class RowModelTest {
    @Test
    public void createColumnModel() throws Exception {
        final Settings settings = new Settings();
        settings.setPage(createPage());
        settings.setColumns(createColumns(3));
        List<ColumnModel> result = RowModel.createColumnModel(createColumnData(3), settings);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getData(), "data #0");
        Assert.assertEquals(result.get(1).getData(), "data #1");
        Assert.assertEquals(result.get(2).getData(), "data #2");
        Assert.assertEquals(result.get(0).getWidth(), 7);
        Assert.assertEquals(result.get(1).getWidth(), 8);
        Assert.assertEquals(result.get(2).getWidth(), 9);
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

    private List<String> createColumnData(final int count) {
        return IntStream.range(0, count).mapToObj(n -> "data #" + n).collect(Collectors.toList());
    }
}
