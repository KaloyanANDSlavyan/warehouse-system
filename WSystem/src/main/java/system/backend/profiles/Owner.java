package system.backend.profiles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Owner extends AbstractProfile {
    @Email(message = "Invalid email address")
    private String emailAddress;
    @Size(max = 30, message = "Phone number is too long")
    @Size(min = 10, message = "Phone number must contain 10 digits")
    //@Pattern(regexp = "(?=.*[0-9]).+", message = "Phone number can contain only digits")
    @Pattern(regexp = "(?!.*[a-z])(?!.*[A-Z])(?!.*[!@#$%^&*)(_=+'|<>~.,?]).*", message = "Phone number can contain only digits")
    private String phoneNumber;
//(?!.*[!@#$%^&*)(-_=+\/'|<>~.,?)
    public Owner(){
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
