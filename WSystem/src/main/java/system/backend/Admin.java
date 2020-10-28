package system.backend;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Pattern(regexp = "^[A-Z][a-z]+$")
    private String firstname;
    @Pattern(regexp = "^[A-Z][a-z]+$")
    private String lastname;
    @Pattern(regexp = "^[aA-zZ0-9_-]{5,30}$")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@*#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    public Admin(){

    }
    public Admin(String firstname, String lastname, String username, String password){
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
