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
public class NotificationServiceHandler {

    @Async
    @EventListener
    public void notifyPatients(PatientDischargeEvent patientDischargeEvent) {
        // Send discharge notification
        log.info("notifyPatients() : Notification Service: Sending discharge notification for patient {} : {}", patientDischargeEvent.getPatientName(), Thread.currentThread().getName());
    }

}