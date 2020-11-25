package system.backend.services;

import system.backend.validators.indicators.ValidationIndicator;

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
    private ValidationIndicator validationIndicator;
    private Long ignoreThisID = null;

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

    public void setValidationIndicator(ValidationIndicator validationIndicator) {
        this.validationIndicator = validationIndicator;
    }

    public ValidationIndicator getValidationIndicator() {
        return validationIndicator;
    }

    public Long getIgnoreThisID() {
        return ignoreThisID;
    }

    public void setIgnoreThisID(Long ignoreThisID) {
        this.ignoreThisID = ignoreThisID;
    }

    public static ValidationService getInstance(){
        if(service == null)
            service = new ValidationService();
        return service;
    }
}
