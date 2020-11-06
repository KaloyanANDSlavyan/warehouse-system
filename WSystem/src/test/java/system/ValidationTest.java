package system;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationTest {
    @Test
    @Ignore
    public void firstLastName() {
        String regex = "^[A-Z][a-z]+$";
        String firstName = "Asf";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(firstName);
        assertTrue(matcher.matches());
    }

    @Test
    @Ignore
    public void username() {
        String regex = "^[aA-zZ0-9_-]{6,30}$";
        //String regex2 = "^[aA-zZ]{2}[aA-zZ]{0,5}[_-]?[aA-zZ0-9]{0,30}$";
        String username = "asdf---";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(username);
        assertTrue(matcher.matches());
    }

    @Test
    @Ignore
    public void password() {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#$%^&+=])(?=\\S+$).{8,}$";
        String password = "@0dA_asdfasdfsadf";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(password);
        assertTrue(matcher.matches());
    }
}
