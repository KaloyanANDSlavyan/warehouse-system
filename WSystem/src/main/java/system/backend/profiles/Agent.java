package system.backend.profiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Agent extends AbstractProfile {
    @Size(max = 50)
    @Email(message = "Invalid email address")
    private String emailAddress;
    @Size(min = 10, message = "Phone number must contain 10 digits")
    @Pattern(regexp = "(?!.*[a-z])(?!.*[A-Z])(?!.*[!@#$%^&*)(_=+'|<>~.,?]).*", message = "Phone number can contain only digits")
    private String phoneNumber;

    public Agent(){
        super();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
