package system.backend.profiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.WSystem;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;

@Entity
public class Admin extends AbstractProfile {

    Admin() {
        super();
    }

    public Admin(String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
        LOGGER = LogManager.getLogger();
    }

    public Set<ConstraintViolation<Object>> createOwner(String firstName, String lastName,
                                                   String username, String pass, String email, String phone) {
        Owner owner = new Owner();
        owner.setFirstname(firstName);
        owner.setLastname(lastName);
        owner.setUsername(username);
        owner.setPassword(pass);
        owner.setEmailAddress(email);
        owner.setPhoneNumber(phone);

        Set<ConstraintViolation<Object>> constraints =
                WSystem.getInstance().createProfile(owner);

        for (ConstraintViolation<Object> con : constraints) {
            LOGGER.error("Agent couldn't be validated");
            LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
            System.out.println(con.getPropertyPath());
            System.out.println(con.getMessage());
        }
        return constraints;
    }

    public Set<ConstraintViolation<Object>> createAgent(String firstName, String lastName,
                                                   String username, String pass, String email, String phone) {
        Agent agent = new Agent();
        agent.setFirstname(firstName);
        agent.setLastname(lastName);
        agent.setUsername(username);
        agent.setPassword(pass);
        agent.setEmailAddress(email);
        agent.setPhoneNumber(phone);

        Set<ConstraintViolation<Object>> constraints =
                WSystem.getInstance().createProfile(agent);

        for (ConstraintViolation<Object> con : constraints) {
            LOGGER.error("Agent couldn't be validated");
            LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
        }
        return constraints;
    }
}
