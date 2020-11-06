package system.backend.profiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
abstract public class AbstractProfile implements Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Size(min = 2, message = "First name is too short")
    @Size(max = 20, message = "First name is too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[0-9]).*", message = "Cannot contain digits."),
            @Pattern(regexp = "(?!.*[!@#$%^&*+=?_]).*", message = "Cannot contain special characters."),
            @Pattern(regexp = "(?![a-z]).*", message = "Should start with a capital letter."),
            @Pattern(regexp = "(?!.+[A-Z]).*", message = "Only the first letter can be capital.")
            //@Pattern(regexp = "(?![A-Z][A-Z])(?!.+[A-Z]).*", message = "Only the first letter can be capital.")
    })
    protected String firstname;
    @Size(min = 2, message = "Last name is too short")
    @Size(max = 20, message = "Last name name is too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[0-9]).*", message = "Cannot contain digits."),
            @Pattern(regexp = "(?!.*[!@#$%^&*+=?_]).*", message = "Cannot contain special characters."),
            @Pattern(regexp = "(?![a-z]).*", message = "Should start with a capital letter."),
            @Pattern(regexp = "(?!.+[A-Z]).*", message = "Only the first letter can be capital.")
    })
    protected String lastname;
    @Size(min = 2, message = "Username is too short")
    @Size(max = 50, message = "Username is too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[!@#$%\\^&\"\':|*+()\\=?,./]).*", message = "Special characters allowed are: -_"),
            @Pattern(regexp = "(?![0-9].*).*", message = "Cannot start with digit"),
            @Pattern(regexp = "(?![-].*).*", message = "Cannot start with hyphon"),
            @Pattern(regexp = "(?!.*[-]$).*", message = "Cannot end with hyphon"),
            @Pattern(regexp = "(?!.*[-].*[-]).*", message = "Hyphon is allowed only once"),
            @Pattern(regexp = "(?!.*[_].*[_]).*", message = "Underscore is allowed only once")
//            @Pattern(regexp = "(?!.*[-]{2,}.*).*", message = "Hyphon is allowed only once in a row"),
//            @Pattern(regexp = "(?!.*[_]{2,}.*).*", message = "Underscore is allowed only once in a row")
    })
    protected String username;
    @Size(min = 8, message = "Password is too short")
    @Size(max = 30, message = "Password is too long")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).*", message = "Must contain at least one digit"),
            @Pattern(regexp = "(?=.*[A-Z]).*", message = "Must contain at least one capital letter"),
            @Pattern(regexp = "(?=.*[a-z]).*", message = "Must contain at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[_@*#$%^&+=]).*", message = "Must contain at least one special character"),
            @Pattern(regexp = "(?=\\S+$).*", message = "Cannot contain spaces"),
    })
    protected String password;
    @Transient
    protected Logger LOGGER;

    AbstractProfile(){
        LOGGER = LogManager.getLogger();
    }

    AbstractProfile(String firstname, String lastname, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
