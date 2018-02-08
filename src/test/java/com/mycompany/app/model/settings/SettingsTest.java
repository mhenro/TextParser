package com.mycompany.app.model.settings;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mhenr on 08.02.2018.
 */
public class SettingsTest {
    @Test
    public void getTotalColumnsOffset() throws Exception {
        final Settings settings = new Settings();
        settings.setPage(createPage());
        settings.setColumns(createColumns(3));
        Assert.assertEquals(33, settings.getTotalColumnsWidth());
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
