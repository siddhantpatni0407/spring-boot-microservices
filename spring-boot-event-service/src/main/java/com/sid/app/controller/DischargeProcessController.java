package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.model.PatientDischargeRequest;
import com.sid.app.service.PatientDischargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
public class DischargeProcessController {

    @Autowired
    private PatientDischargeService patientDischargeService;

    @PostMapping(value = AppConstants.DISCHARGE_PATIENT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String dischargePatient(@RequestBody PatientDischargeRequest request) {
        if (log.isInfoEnabled()) {
            log.info("dischargePatient() : Discharge Patient Event - START");
        }
        return patientDischargeService.dischargePatient(request.getPatientId(), request.getPatientName());
    }

}