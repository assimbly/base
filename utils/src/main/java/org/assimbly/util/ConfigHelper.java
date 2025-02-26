package org.assimbly.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigHelper {

    private String fileName;
    private Class<?> originClass;

    public ConfigHelper(String fileName, Class<?> originClass) {
        this.fileName = fileName;
        this.originClass = originClass;
    }

    /**
     * Get a property by its key from the module's config file.
     *
     * @param key to get the property by.
     * @return the found property.
     */
    public String get(String key) {
        try {
            Properties props = loadFile(fileName);
            return props.getProperty(key);
        } catch (IOException e) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, null, e);
        }

        return "";
    }

    /**
     * Load the contents of the module's config file into a properties object.
     *
     * @param fileName to get the config file by.
     * @return a Properties object representing all the properties.
     * @throws IOException when something went wrong while loading the file.
     */
    private Properties loadFile(String fileName) throws IOException {
        ClassLoader loader = originClass.getClassLoader();
        Properties props = new Properties();
        InputStream resourceStream = null;
        FileInputStream fileInputStream = null;

        try {
            resourceStream = loader.getResourceAsStream(fileName);
            fileInputStream = new FileInputStream("/opt/karaf/etc/" + fileName);
            props.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(resourceStream!=null) {
                resourceStream.close();
            }
            if(fileInputStream!=null) {
                fileInputStream.close();
            }
        }

        return props;
    }

}
