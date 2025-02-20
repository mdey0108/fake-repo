package com.phh.runreport.config;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("C:/config/app.config")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getEmailHost() {
        return properties.getProperty("email.host");
    }

    public int getEmailPort() {
        return Integer.parseInt(properties.getProperty("email.port"));
    }

    public String getEmailUsername() {
        return properties.getProperty("email.username");
    }

    public String getEmailPassword() {
        return properties.getProperty("email.password");
    }

    public String getNoDataEmailBody() {
        return properties.getProperty("email.no.data.body");
    }

    public String getLogFileLocation() {
        return properties.getProperty("log.file.location");
    }
}