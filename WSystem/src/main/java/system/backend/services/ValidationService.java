package system.backend.services;

import system.application.controllers.RegisterOwnerController;
import system.backend.dao.AbstractDAO;
import system.backend.profiles.AbstractProfile;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.profiles.Profile;
import system.backend.validators.indicators.ValidationIndicator;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import java.util.Set;

//This class will be used where validation is needed
public class ValidationService<T> {
    private static ValidationService service;
    private ValidatorFactory factory;
    private Validator validator;
    private Long ignoreThisID = null;

    public ValidationService(){
        createFactory();
        createValidator();
    }

    private void createFactory(){
        factory = Validation.buildDefaultValidatorFactory();
    }

    private void createValidator(){
        validator = factory.getValidator();
    }

    public Set<ConstraintViolation<T>> validate(T object){
        Set<ConstraintViolation<T>> constraints = validator.validate(object);
        return constraints;
    }

    public Long getIgnoreThisID() {
        return ignoreThisID;
    }

    public void setIgnoreThisID(Long ignoreThisID) {
        this.ignoreThisID = ignoreThisID;
    }
}
