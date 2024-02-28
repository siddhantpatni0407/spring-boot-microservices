package com.sid.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
public class SchedulerService {

    /*@Autowired
    private ThreadPoolTaskScheduler taskScheduler;*/

    @Scheduled(cron = "${cron.expression.value}")
    public void scheduler() {
        if (log.isInfoEnabled()) {
            log.info("schedulerDemo() : Scheduler demo : {}", Calendar.getInstance().getTime());
        }
    }

}