package main.auditoriumupdateservice.services;

import main.auditoriumupdateservice.model.Auditorium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationService.class);

    public boolean validateAuditoriumData(Auditorium auditorium) {
        if (auditorium.getId() == null || auditorium.getId().isEmpty()) {
            LOGGER.warn("Validation Error: Missing or empty field 'id' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getNumber() == null || auditorium.getNumber().isEmpty()) {
            LOGGER.warn("Validation Error: Missing or empty field 'number' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getName() == null || auditorium.getName().isEmpty()) {
            LOGGER.warn("Validation Error: Missing or empty field 'name' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getBuilding() == null || auditorium.getBuilding().isEmpty()) {
            LOGGER.warn("Validation Error: Missing or empty field 'building' for auditorium: {}", auditorium);
            return false;
        }
        return true;
    }
}
