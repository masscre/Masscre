package go.web.validator;
import go.web.model.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import go.web.controller.*;
import go.dto.Database;


public class LoginValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Login.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Login login = (Login) obj;   
        login.login();
               
        if (login.getUsername() == null || login.getUsername().length() == 0) {
            errors.rejectValue("username", "error.empty.field", "Please enter username");            
        }
        else if (login.getPassword() == null || login.getPassword().length() == 0) {
            errors.rejectValue("password", "error.empty.field", "Please enter password");            
        }
        else if (!Database.database.userExist(login.getUsername())) {
            errors.rejectValue("username", "unknown.user", "Unknown User");            
        }        
        else if (login.loggedIn() == false) {
            errors.rejectValue("password", "wrong.password", "Wrong password");            
        } 
        System.out.println(login.getPassword());
    }
}



 
