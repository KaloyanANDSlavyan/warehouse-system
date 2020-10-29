package system.backend;

import javax.persistence.*;

@Entity
public class Admin extends Profile {

    public Admin(){
        super();
    }

    public Admin(String firstname, String lastname, String username, String password){
        super(firstname, lastname, username, password);
    }
}
