package org.jee.clinicmanager.util;

import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AppointmentRules {
    public static final int BOOKING_LEAD_HOURS = 2;
    public static final int CANCELLATION_LEAD_HOURS = 12;
    public static final int BUFFER_DURATION_MINUTES = 5;
    public static final int SLOT_DURATION_MINUTES = 30;
    public static final LocalTime LUNCH_START_BREAK_TIME = LocalTime.of(12, 0);
    public static final LocalTime LUNCH_END_BREAK_TIME = LocalTime.of(13, 0);

    private AppointmentRules() {}

    public static boolean canCancel(Appointment appointment) {
        if (!appointment.getStatus().equals(AppointmentStatus.PLANNED)) return false;

        LocalDateTime dateStartingTime = LocalDateTime.of(appointment.getDate(), appointment.getStartTime());

        return ChronoUnit.HOURS.between(LocalDateTime.now(), dateStartingTime) >= AppointmentRules.CANCELLATION_LEAD_HOURS;
    }

   // I moved canReschedule and availableSlot to AppointmentService since they rely on repository

    public static String safeConcat(String firstName, String lastName) {
        if (firstName == null && lastName == null) return null;
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }

    public static int slotWithBufferMinutes() {
        return AppointmentRules.SLOT_DURATION_MINUTES + AppointmentRules.BUFFER_DURATION_MINUTES;
    }


}
