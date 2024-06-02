package ru.intership.logistservice.validator;

import org.springframework.stereotype.Component;
import ru.intership.logistservice.exception.NotEnoughRightsException;
import ru.intership.logistservice.model.UserRole;

import java.util.Set;

@Component
public class UserValidator {

    public void validateUserIsCompanyLogist(String companyInn, Set<String> roles) {
        if (!roles.contains(companyInn + UserRole.LOGIST.name())) {
            throw new NotEnoughRightsException("User is not a company logist");
        }
    }
}
