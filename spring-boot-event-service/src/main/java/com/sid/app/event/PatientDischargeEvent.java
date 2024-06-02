package com.sid.app.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Siddhant Patni
 */

@Getter
@Setter
public class PatientDischargeEvent extends ApplicationEvent {

    private String patientId;
    private String patientName;

    public PatientDischargeEvent(Object source, String patientId, String patientName) {
        super(source);
        this.patientId = patientId;
        this.patientName = patientName;
    }

}