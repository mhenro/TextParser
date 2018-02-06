package com.mycompany.app;

import com.mycompany.app.model.settings.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JAXBException {
        final Settings settings = readSettings(args);


        if (settings != null) {
            if (settings.getPage() != null) {
                System.out.println("page width = " + settings.getPage().getWidth());
                System.out.println("page height = " + settings.getPage().getHeight());
            }
            if (settings.getColumns() != null) {
                System.out.println(settings.getColumns().stream().map(column -> {
                    return "column-title=" + column.getTitle() + ", column-width=" + column.getWidth();
                }).collect(Collectors.joining("; ")));
            }
        }

    }

    private static Settings readSettings(final String[] args) throws JAXBException {
        if (args.length < 3) {
            System.out.println("Wrong args. Usage: TODO");
            System.exit(0);
        }
        final String settingsPath = args[0];
        final String dataPath = args[1];
        final String destPath = args[2];
        System.out.println( "settings path = " + settingsPath);
        System.out.println( "data path = " + dataPath);
        System.out.println( "dest path = " + destPath);

        final JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final File xml = new File(settingsPath);

        return (Settings)unmarshaller.unmarshal(xml);
    }
}
