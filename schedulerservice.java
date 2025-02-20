package com.phh.runreport.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SchedulerService {

    private static final Logger logger = LogManager.getLogger(SchedulerService.class);

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private EmailService emailService;

    public void run() {
        logger.info("Starting data export process...");
        String csvFilePath = databaseService.exportDataToCsv();

        if (csvFilePath != null) {
            File csvFile = new File(csvFilePath);
            boolean hasData = csvFile.length() > 0; // Check if the file has data

            if (hasData) {
                logger.info("Data found. Sending email with attachment...");
            } else {
                logger.warn("No data found in the database.");
            }

            emailService.sendEmailWithAttachment(csvFilePath, hasData);
        } else {
            logger.error("Failed to export data to CSV.");
            emailService.sendFailureNotification("Failed to export data to CSV.");
        }
    }
}