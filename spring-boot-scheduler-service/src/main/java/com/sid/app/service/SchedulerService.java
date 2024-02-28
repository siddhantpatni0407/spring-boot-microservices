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
    public void scheduler1() {
        if (log.isInfoEnabled()) {
            log.info("schedulerDemo() : Scheduler 1 : {}", Calendar.getInstance().getTime());
        }
    }

    @Scheduled(cron = "${cron.expression.value}")
    public void scheduler2() {
        if (log.isInfoEnabled()) {
            log.info("schedulerDemo() : Scheduler 2 : {}", Calendar.getInstance().getTime());
        }
    }

}