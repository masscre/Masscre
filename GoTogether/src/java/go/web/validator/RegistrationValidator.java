/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.validator;


import go.dto.Database;
import go.web.model.Registration;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class RegistrationValidator implements Validator {
    
    @Override
    public boolean supports(Class clazz) {
        return Registration.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
        Registration reg = (Registration) obj;
        boolean ok = true;        
        
        if (reg.getFirstname() == null || reg.getFirstname().length() == 0) {
            errors.rejectValue("firstname", "error.empty.field", "Please enter firstname.");  
            ok = false;
        }
        
        if (reg.getLastname() == null || reg.getLastname().length() == 0) {
            errors.rejectValue("lastname", "error.empty.field", "Please enter lastname.");  
            ok = false;
        }
        
        if (reg.getUsername() == null || reg.getUsername().length() == 0) {
            errors.rejectValue("username", "error.empty.field", "Please enter username.");  
            ok = false;
        }
        
        if (reg.getEmail() == null || reg.getEmail().length() == 0) {
            errors.rejectValue("email", "error.empty.field", "Please enter email.");  
            ok = false;
        } else {
        
        if (!reg.getEmail().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            errors.rejectValue("email", "error.empty.field", "Incorect email address."); 
        } }
        
        if (Database.database.userExist(reg.getUsername()) == true) {
            errors.rejectValue("username", "error.empty.field", "Username is taken. Please use another one.");  
            ok = false;
        }
        
        if (reg.getPassword() == null || reg.getPassword().length() == 0) {
            errors.rejectValue("password", "error.empty.field", "Please enter password.");  
            ok = false;
        }
        
        if (reg.getPassword().equals(reg.getPasswordcheck()) != true) {
            errors.rejectValue("passwordcheck", "error.empty.field", "Passwords don`t match.");
        }
        
        if (ok == true) {
            Database.database.register(reg.getFirstname(), reg.getLastname(), reg.getUsername(), reg.getEmail(), reg.getPassword());
        }
    }
}
