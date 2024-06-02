package com.sid.app.event.handler;

import com.sid.app.event.PatientDischargeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Siddhant Patni
 */
@Component
@Slf4j
public class HousekeepingServiceHandler {

    @Async
    @EventListener
    public void cleanAndAssign(PatientDischargeEvent patientDischargeEvent) {
        // Prepare the room for the next patient
        log.info("cleanAndAssign() : Housekeeping Service: Preparing room for next patient after discharge of {} : {}", patientDischargeEvent.getPatientId(), Thread.currentThread().getName());
    }

}