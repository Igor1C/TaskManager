package com.igor1c.taskmanager.helpers;

import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    private static String PORT = "32380";
    private static String DB_PATH = "jdbc:h2:file:./db/taskmanager";
    private static String DB_USER = "sa";
    private static String DB_PASSWORD = "";
    private static int TICK_INTERVAL = 60;

    private static Properties properties;

    static {
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);

            PORT = properties.getProperty("port");

            DB_PATH = properties.getProperty("spring.datasource.url");
            DB_USER = properties.getProperty("spring.datasource.username");
            DB_PASSWORD = properties.getProperty("spring.datasource.password");

            TICK_INTERVAL = Integer.parseInt(properties.getProperty("tickInterval"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String getPORT() {
        return PORT;
    }

    public static String getDbPath() {
        return DB_PATH;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static int getTickInterval() {
        return TICK_INTERVAL;
    }
}
