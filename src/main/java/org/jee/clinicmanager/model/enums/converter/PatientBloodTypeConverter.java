package org.jee.clinicmanager.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jee.clinicmanager.model.enums.PatientBloodType;

@Converter(autoApply = true)
public class PatientBloodTypeConverter implements AttributeConverter<PatientBloodType, String> {
    @Override
    public String convertToDatabaseColumn(PatientBloodType attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public PatientBloodType convertToEntityAttribute(String dbData) throws IllegalArgumentException {
        return dbData == null ? null : PatientBloodType.fromValue(dbData);
    }
}
