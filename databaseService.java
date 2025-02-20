package com.phh.runreport.service;

import com.phh.runreport.config.ConfigReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DatabaseService {

    @Autowired
    private ConfigReader configReader;

    public String exportDataToCsv() {
        String csvFilePath = configReader.getProperty("attachment.remote.location") + "/" +
                configReader.getProperty("attachment.name") + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

        try (Connection connection = DriverManager.getConnection(
                configReader.getProperty("db.url"),
                configReader.getProperty("db.username"),
                configReader.getProperty("db.password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(configReader.getProperty("query"));
             FileWriter writer = new FileWriter(csvFilePath)) {

            // Write CSV header
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                writer.append(metaData.getColumnName(i));
                if (i < columnCount) writer.append(",");
            }
            writer.append("\n");

            // Write CSV data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(resultSet.getString(i));
                    if (i < columnCount) writer.append(",");
                }
                writer.append("\n");
            }

            return csvFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}