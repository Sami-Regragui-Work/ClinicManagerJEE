package org.jee.clinicmanager.exception.appointment;

public class UnauthorizedActionException extends AppointmentException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
