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
public class BillingServiceHandler {

    @Async
    @EventListener
    public void processBill(PatientDischargeEvent patientDischargeEvent) {
        // Finalize billing details
        log.info("processBill() : Billing Service: Finalizing bill for patient {} : {}", patientDischargeEvent.getPatientId(), Thread.currentThread().getName());
    }

}