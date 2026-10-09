package org.jee.clinicmanager.exception.appointment;

public class UnavailableSlotException extends AppointmentException {
    public UnavailableSlotException(String message) {
        super(message);
    }
}
