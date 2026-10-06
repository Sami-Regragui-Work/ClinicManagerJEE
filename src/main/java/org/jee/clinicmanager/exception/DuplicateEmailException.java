package org.jee.clinicmanager.exception;

public class DuplicateEmailException extends UserException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
