package com.example.service;

import com.example.dto.ValidationRequest;
import com.example.dto.ValidationResponse;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.common.annotation.Blocking;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
public class ValidationService {

    private static final Logger LOG = Logger.getLogger(ValidationService.class);

    private static final List<String> FORBIDDEN_NAMES = List.of(
        "schlecht", "idiot", "mistvieh", "dumm", "böse", "alex"
    );


    @Incoming("validation-requests")
    @Outgoing("validation-responses-out")
    @Blocking  // Da die Methode synchron ist
    public ValidationResponse validate(ValidationRequest message) {
        LOG.info("Validating request: " + message);
    
        // 🔹 Name auf verbotene Wörter prüfen
        boolean isValid = validateName(message.name());
    
        LOG.infof("Validation result for name '%s': %s", message.name(), isValid);
    
        return new ValidationResponse(message.id(), isValid);
    }


    private boolean validateName(String name) {
    
        if (name == null || name.trim().isEmpty()) {
            LOG.warn("Validation failed: Name is empty.");
            return false;
        }
    
        for (String word : FORBIDDEN_NAMES) {
            if (name.equalsIgnoreCase(word)) {
                LOG.warnf("Validation failed: Forbidden name '%s' found in name.", word);
                return false;
            }
        }
        return true;
    }
}
