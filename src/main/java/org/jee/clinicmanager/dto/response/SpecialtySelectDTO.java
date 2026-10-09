package org.jee.clinicmanager.dto.response;

public class SpecialtySelectDTO {
    private final Long id;
    private final String name;

    public SpecialtySelectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
