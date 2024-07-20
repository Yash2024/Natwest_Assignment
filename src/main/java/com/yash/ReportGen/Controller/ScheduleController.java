package com.yash.ReportGen.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.ReportGen.service.ReportService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ReportService scheduleService;

    @PostMapping("/update")
    public String updateSchedule(@RequestParam("cron") String cronExpression) {
        scheduleService.setCronExpression(cronExpression);
        
        System.setProperty("schedule.cron.expression", cronExpression);
        return "Schedule updated to: " + cronExpression;
    }

    @GetMapping("/current")
    public String getCurrentSchedule() {
        return "Current schedule: " + scheduleService.getCronExpression();
    }
}
