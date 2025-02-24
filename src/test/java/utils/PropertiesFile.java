package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    private static FileInputStream fis;
    private static Properties prop = null;


    /**
     * Reads properties from config.properties of given property name
     *
     * @param property property to get from file
     * @return property as a string
     **/
    public static String getProperty(String property) {
        try {
            fis = new FileInputStream(new File("src\\config.properties").getAbsolutePath());
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Properties File Not Found" + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IO Exception while loading Properties File" + ioe.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing file input stream" + e.getMessage());
            }
        }
        return prop.getProperty(property).trim();
    }

}
