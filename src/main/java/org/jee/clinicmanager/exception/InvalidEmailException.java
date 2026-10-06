package org.jee.clinicmanager.exception;

public class InvalidEmailException extends UserException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
