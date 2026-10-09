package org.jee.clinicmanager.exception.user;

public class InvalidEmailException extends UserException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
