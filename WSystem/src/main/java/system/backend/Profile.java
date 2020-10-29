package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Pattern(regexp = "^[A-Z][a-z]+$")
    protected String firstname;
    @Pattern(regexp = "^[A-Z][a-z]+$")
    protected String lastname;
    @Pattern(regexp = "^[aA-zZ0-9_-]{5,30}$")
    protected String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@*#$%^&+=])(?=\\S+$).{8,}$")
    protected String password;
    @Transient
    protected Logger LOGGER;

    Profile(){
        LOGGER = LogManager.getLogger();
    }

    Profile(String firstname, String lastname, String username, String password){
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
