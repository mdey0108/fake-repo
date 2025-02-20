package com.phh.runreport.service;

import com.phh.runreport.config.ConfigReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private ConfigReader configReader;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configReader.getEmailHost());
        mailSender.setPort(configReader.getEmailPort());
        mailSender.setUsername(configReader.getEmailUsername());
        mailSender.setPassword(configReader.getEmailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public void sendEmailWithAttachment(String attachmentPath, boolean hasData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(configReader.getProperty("email.from"));
            helper.setTo(configReader.getProperty("email.to"));
            helper.setSubject(configReader.getProperty("email.subject"));
            helper.setText(hasData ? configReader.getProperty("email.body") : configReader.getNoDataEmailBody());

            if (hasData) {
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment(file.getFilename(), file);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendFailureNotification(String errorMessage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(configReader.getProperty("email.from"));
            helper.setTo(configReader.getProperty("failure.email"));
            helper.setSubject("Data Export Failure");
            helper.setText("Error: " + errorMessage);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}