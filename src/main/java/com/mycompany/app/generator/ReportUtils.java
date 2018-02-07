package com.mycompany.app.generator;

import com.mycompany.app.model.report.ReportModel;
import com.mycompany.app.model.settings.Settings;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by berlogic on 2/7/18.
 */
public abstract class ReportUtils {
    public static void generateReport(final Settings settings, final ReportModel report) throws IOException {
        List<String> strings = report.getStrings();
        if (settings.getDestPath() == null || settings.getDestPath().isEmpty()) {
            strings.forEach(System.out::println);
        } else {
            saveToFile(settings, strings);
        }
    }

    public static ReportModel createReportModel(final Settings settings) throws IOException {
        final File file = new File(settings.getDataPath());
        final InputStream is = new FileInputStream(file);
        final BufferedReader buf = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-16")));
        String line;
        String[] dataArr;
        List<List<String>> reportData = new ArrayList<>();

        while(true) {
            line = buf.readLine();
            if (line == null) {
                break;
            } else {
                dataArr = line.split("\t");
                if (dataArr.length != settings.getColumns().size()) {
                    throw new RuntimeException("Data is not compatible with settings!");
                }
                reportData.add(Arrays.asList(dataArr));
            }
        }
        buf.close();

        final ReportModel report = new ReportModel(settings, reportData);
        return report;
    }

    private static void saveToFile(final Settings settings, final List<String> strings) throws IOException {
        final File file = new File(settings.getDestPath());
        final OutputStream os = new FileOutputStream(file);
        final OutputStreamWriter writer = new OutputStreamWriter(os, Charset.forName("UTF-16"));
        for (final String str : strings) {
            writer.write(str);
        }
        writer.close();
    }
}
