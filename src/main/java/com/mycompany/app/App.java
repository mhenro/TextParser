package com.mycompany.app;

import com.mycompany.app.generator.ReportUtils;
import com.mycompany.app.model.report.ReportModel;
import com.mycompany.app.model.settings.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class App 
{
    public static void main( String[] args ) throws JAXBException, IOException {
        final Settings settings = readSettings(args);
        final ReportModel report = ReportUtils.createReportModel(settings);

        ReportUtils.generateReport(settings, report);
    }

    private static Settings readSettings(final String[] args) throws JAXBException {
        if (args.length < 2) {
            System.out.println("Wrong args. Usage: java -jar TextParser.jar settings.xml data.tsv [report.txt]");
            System.exit(0);
        }
        final String settingsPath = args[0];
        final String dataPath = args[1];
        final String destPath = args.length > 2 ? args[2] : null;

        final JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final File xml = new File(settingsPath);

        final Settings settings = (Settings)unmarshaller.unmarshal(xml);
        if (settings.getTotalColumnsWidth() > settings.getPage().getWidth()) {
            throw new RuntimeException("Wrong settings: Sum of column's width is larger than whole page width!");
        }
        settings.setDataPath(dataPath);
        if (destPath != null) {
            settings.setDestPath(destPath);
        }

        return settings;
    }
}
