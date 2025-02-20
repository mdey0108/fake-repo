package com.phh.runreport;

import com.phh.runreport.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunReportApplication implements CommandLineRunner {

    @Autowired
    private SchedulerService schedulerService;

    public static void main(String[] args) {
        SpringApplication.run(RunReportApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        schedulerService.run();
    }
}