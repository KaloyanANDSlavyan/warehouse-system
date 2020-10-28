package system.backend;

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

    public Set<ConstraintViolation<Admin>> validateAdmin(Admin admin){
        Set<ConstraintViolation<Admin>> constraints = validator.validate(admin);
        return constraints;
    }

    public static ValidationService getInstance(){
        if(service == null)
            service = new ValidationService();
        return service;
    }
}
