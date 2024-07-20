package com.yash.ReportGen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yash.ReportGen.Controller.Rest;

@Service
public class  ReportService{

    @Autowired
    private Rest reportGenerationController;

    private String cronExpression = "0 0 0 * * *"; // Runs every day at midnight

    @Scheduled(cron = "${schedule.cron.expression}")
    public void generateReport() {
        try {
            reportGenerationController.produceOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}