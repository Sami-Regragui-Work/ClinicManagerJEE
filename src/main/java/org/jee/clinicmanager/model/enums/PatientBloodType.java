package org.jee.clinicmanager.model.enums;

public enum PatientBloodType {
    APLUS("A+"),
    AMINUS("A-"),
    BPLUS("B+"),
    BMINUS("B-"),
    ABPLUS("AB+"),
    ABMINUS("AB-"),
    OPLUS("O+"),
    OMINUS("O-");

    private final String value;

    PatientBloodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static PatientBloodType fromValue(String value) throws IllegalArgumentException {
        if (value == null) return null;
        for (PatientBloodType CONSTANT : PatientBloodType.values()) {
            if (CONSTANT.value.equals(value)) return CONSTANT;
        }
        throw new IllegalArgumentException("Invalid blood type can't be converted");
    }
}
