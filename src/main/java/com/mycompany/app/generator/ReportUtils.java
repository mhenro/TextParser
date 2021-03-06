package com.mycompany.app.generator;

import com.mycompany.app.model.report.ReportModel;
import com.mycompany.app.model.settings.Settings;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ReportUtils {
    public static void generateReport(final Settings settings, final ReportModel report) throws IOException {
        List<String> strings = report.getStrings();
        if (settings.getDestPath() == null || settings.getDestPath().isEmpty()) {
            strings.forEach(System.out::print);
        } else {
            saveToFile(settings, strings);
        }
    }

    public static ReportModel createReportModel(final Settings settings) throws IOException {
        List<List<String>> reportData = loadDataFromFile(settings);
        final ReportModel report = new ReportModel(settings, reportData);
        return report;
    }

    public static String fillEmptySpace(final String str, final int maxLength) {
        if (str.length() < maxLength) {
            final String emptySpace = IntStream.range(0, maxLength - str.length())
                                                .mapToObj(i -> ReportModel.SPACE)
                                                .collect(Collectors.joining(""));
            return str + emptySpace;
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
        final String[] words = str.split("(?<=[^A-Za-z0-9\\p{InCYRILLIC}])");
        result.clear();
        String accum = "";
        for (String word : words) {
            if (word.length() > maxLength) {
                String first = word.substring(0, maxLength);
                String second = word.substring(maxLength);
                if (accum.length() + first.length() > maxLength) {
                    result.add(accum);
                    accum = "";
                }
                result.add(first);
                accum = parseWord(result, accum, second, maxLength);
            } else {
                accum = parseWord(result, accum, word, maxLength);
            }
        }
        result.add(accum);

        return result;
    }

    /* recursive method */
    private static String parseWord(final List<String> storage, final String accum, final String word, final int maxLength) {
        String newAccum = accum;
        final int lastIndex = storage.size() - 1;
        if (newAccum.isEmpty() && !storage.isEmpty() && storage.get(lastIndex).length() + word.length() <= maxLength) {
            newAccum = storage.get(lastIndex);
            storage.remove(lastIndex);
            newAccum = parseWord(storage, newAccum, word, maxLength);
        } else if (accum.length() + word.length() < maxLength) {
            newAccum += word;
        } else if (accum.length() + word.length() == maxLength) {
            newAccum += word;
        } else if (accum.length() + word.replaceAll(" +$", "").length() == maxLength) { //remove last space if needed
            newAccum += word.replaceAll(" +$", "");
        } else if (word.length() > maxLength){
            storage.addAll(splitString(word, maxLength));
        } else{
            storage.addAll(Arrays.asList(newAccum));
            newAccum = parseWord(storage, word, "", maxLength);
        }
        return newAccum;
    }

    private static List<List<String>> loadDataFromFile(final Settings settings) throws IOException {
        final File file = new File(settings.getDataPath());
        final InputStream is = new FileInputStream(file);
        final List<List<String>> reportData = new ArrayList<>();
        try (final BufferedReader buf = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-16")))) {
            String line;
            String[] dataArr;

            while (true) {
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
        }
        return reportData;
    }

    private static void saveToFile(final Settings settings, final List<String> strings) throws IOException {
        final File file = new File(settings.getDestPath());
        final OutputStream os = new FileOutputStream(file);
        try (final OutputStreamWriter writer = new OutputStreamWriter(os, Charset.forName("UTF-16"))) {
            for (final String str : strings) {
                writer.write(str);
            }
        }
    }
}
