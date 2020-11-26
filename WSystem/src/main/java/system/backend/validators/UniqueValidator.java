package system.backend.validators;

import system.backend.WSystem;
import system.backend.constraints.MyUnique;
import system.backend.others.Indicator;
import system.backend.profiles.Admin;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.services.AgentValidation;
import system.backend.services.OwnerValidation;
import system.backend.services.ValidationService;
import system.backend.validators.indicators.ValidationIndicator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<MyUnique, String> {

    private Class<?>[] type;
    private String column;

    @Override
    public void initialize(MyUnique constraintAnnotation) {
        this.type = constraintAnnotation.type();
        this.column = constraintAnnotation.column();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {

//        ValidationService validationService = ValidationService.getInstance();
//        ValidationIndicator indicator = validationService.getValidationIndicator();
//        Long ignoreThisID = validationService.getIgnoreThisID();
        ValidationIndicator indicator = Indicator.getInstance().getValidationIndicator();
        WSystem wSystem = WSystem.getInstance();

        for (Class<?> type : type) {
            if (type == Owner.class && indicator == ValidationIndicator.OWNER) {
                System.out.println("Performing OWNER");
                OwnerValidation ownerValidation = wSystem.getOwnerValidation();
                Long ignoreThisID = ownerValidation.getIgnoreThisID();

                if(ignoreThisID == null) {
                    Owner owner = wSystem.getOwnerDAO().findBy1Value(Owner.class, column, object);
                    Agent agent = wSystem.getAgentDAO().findBy1Value(Agent.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = wSystem.getAdminDAO().findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
                else {
                    Owner owner = wSystem.getOwnerDAO().findBy1ValueExcept(Owner.class, column, object, ignoreThisID);
                    Agent agent = wSystem.getAgentDAO().findBy1Value(Agent.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = wSystem.getAdminDAO().findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
            }
            else if(type == Agent.class && indicator == ValidationIndicator.AGENT){
                System.out.println("Performing Agent");
                AgentValidation agentValidation = wSystem.getAgentValidation();
                Long ignoreThisID = agentValidation.getIgnoreThisID();

                if(ignoreThisID == null) {
                    Agent agent = wSystem.getAgentDAO().findBy1Value(Agent.class, column, object);
                    Owner owner = wSystem.getOwnerDAO().findBy1Value(Owner.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = wSystem.getAdminDAO().findBy1Value(Admin.class, column, object);
                    }

                    if(agent != null || owner != null || admin != null)
                        return false;
                }
                else {
                    Agent agent = wSystem.getAgentDAO().findBy1ValueExcept(Agent.class, column, object, ignoreThisID);
                    Owner owner = wSystem.getOwnerDAO().findBy1Value(Owner.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = wSystem.getAdminDAO().findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
            }
            else if(type == Admin.class && indicator == ValidationIndicator.ADMIN){
                System.out.println("Performing Admin");
                Admin admin = wSystem.getAdminDAO().findBy1Value(Admin.class, column, object);
                Agent agent = wSystem.getAgentDAO().findBy1Value(Agent.class, column, object);
                Owner owner = wSystem.getOwnerDAO().findBy1Value(Owner.class, column, object);

                if(admin != null || agent != null || owner != null)
                    return false;
            }
        }
        return true;
    }
}