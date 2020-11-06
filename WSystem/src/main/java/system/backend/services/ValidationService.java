package system.backend.services;

import system.backend.profiles.AbstractProfile;
import system.backend.profiles.Profile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import java.util.Set;

//This class will be used where validation is needed
public class ValidationService {
    private static ValidationService service;
    private ValidatorFactory factory;
    private Validator validator;

    ValidationService(){
        createFactory();
        createValidator();
    }

    private void createFactory(){
        factory = Validation.buildDefaultValidatorFactory();
    }

    private void createValidator(){
        validator = factory.getValidator();
    }

    public Set<ConstraintViolation<Object>> validate(Object profile){
        Set<ConstraintViolation<Object>> constraints = validator.validate(profile);
        return constraints;
    }

    public static ValidationService getInstance(){
        if(service == null)
            service = new ValidationService();
        return service;
    }
}
