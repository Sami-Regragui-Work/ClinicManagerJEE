package org.jee.clinicmanager.exception.appointment;

public class LeadTimeViolationException extends AppointmentException {
    public LeadTimeViolationException(String message) {
        super(message);
    }
}
