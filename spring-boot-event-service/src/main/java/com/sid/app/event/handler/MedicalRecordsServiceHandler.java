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
public class MedicalRecordsServiceHandler {

    @Async
    @EventListener
    public void updatePatientHistory(PatientDischargeEvent patientDischargeEvent) {
        // Update medical records
        log.info("updatePatientHistory() : Medical Records Service: Updating records for patient {} : {}", patientDischargeEvent.getPatientId(), Thread.currentThread().getName());
    }

}