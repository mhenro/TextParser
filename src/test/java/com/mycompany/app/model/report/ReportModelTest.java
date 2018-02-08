package com.mycompany.app.model.report;

import com.mycompany.app.generator.ReportUtils;
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
public class ReportModelTest {
    @Test
    public void getStrings() throws Exception {
        final Settings settings = createSettings();
        final ReportModel model = ReportUtils.createReportModel(settings);
        List<String> strings = model.getStrings();
        Assert.assertEquals(15, strings.size());
        Assert.assertEquals("| Title # | Title #1 | Title #2  |\r\n| 0       |          |           |\r\n", strings.get(0));
        Assert.assertEquals("--------------------------------\r\n", strings.get(1));
        Assert.assertEquals("| 1       | 25/11    | Павлов    |\r\n|         |          | Дмитрий   |\r\n", strings.get(2));
        Assert.assertEquals("--------------------------------\r\n", strings.get(3));
        Assert.assertEquals("| 2       | 26/11    | Павлов    |\r\n|         |          | Константи |\r\n|         |          | н         |\r\n", strings.get(4));
        Assert.assertEquals("--------------------------------\r\n", strings.get(5));
        Assert.assertEquals("| 3       | 27/11    | Н/Д       |\r\n", strings.get(6));
        Assert.assertEquals("--------------------------------\r\n", strings.get(7));
        Assert.assertEquals("~\r\n", strings.get(8));
        Assert.assertEquals("| Title # | Title #1 | Title #2  |\r\n| 0       |          |           |\r\n", strings.get(9));
        Assert.assertEquals("--------------------------------\r\n", strings.get(10));
        Assert.assertEquals("| 4       | 28/11    | Ким Чен   |\r\n|         |          | Ир        |\r\n", strings.get(11));
        Assert.assertEquals("--------------------------------\r\n", strings.get(12));
        Assert.assertEquals("| 5       | 29/11/   | Юлианна-  |\r\n|         | 2009     | Оксана    |\r\n|         |          | Сухово-   |\r\n|         |          | Кобылина  |\r\n", strings.get(13));
        Assert.assertEquals("--------------------------------\r\n", strings.get(14));
        int a = 10;
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
