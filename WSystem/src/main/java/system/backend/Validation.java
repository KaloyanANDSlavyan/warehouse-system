package system.backend;

//This class will be used where validation is needed
public class Validation {
    private static Validation validation;

    public static Validation getInstance(){
        if(validation == null)
            validation = new Validation();
        return validation;
    }

    public boolean username(String username){
        return true;
    }
    public boolean password(String password){
        return true;
    }
}
