package org.jee.clinicmanager.dto.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class AvailableSlotDTO {
    private final LocalTime slotTime;
    private final String doctorFullName;
    private final String departmentName;
    private final Long slotId;

    public AvailableSlotDTO(LocalTime slotTime, String doctorFullName, String departmentName, Long slotId) {
        this.slotTime = slotTime;
        this.doctorFullName = doctorFullName;
        this.departmentName = departmentName;
        this.slotId = slotId;
    }

    public LocalTime getSlotTime() {
        return slotTime;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Long getSlotId() {
        return slotId;
    }
}
