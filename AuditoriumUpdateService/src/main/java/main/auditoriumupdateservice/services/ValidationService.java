package main.auditoriumupdateservice.services;

import main.auditoriumupdateservice.model.Auditorium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    public boolean validateAuditoriumData(Auditorium auditorium) {
        if (auditorium.getId() == null || auditorium.getId().isEmpty()) {
            logger.warn("Validation Error: Missing or empty field 'id' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getNumber() == null || auditorium.getNumber().isEmpty()) {
            logger.warn("Validation Error: Missing or empty field 'number' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getName() == null || auditorium.getName().isEmpty()) {
            logger.warn("Validation Error: Missing or empty field 'name' for auditorium: {}", auditorium);
            return false;
        }

        if (auditorium.getBuilding() == null || auditorium.getBuilding().isEmpty()) {
            logger.warn("Validation Error: Missing or empty field 'building' for auditorium: {}", auditorium);
            return false;
        }
        return true;
    }
}
