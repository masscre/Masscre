/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.validator;


import go.dto.Database;
import go.web.model.Friend;
import go.web.model.User;
import java.util.ArrayList;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class AddfriendValidator implements Validator {
    
    @Override
    public boolean supports(Class clazz) {
        return Friend.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
        Friend friend = (Friend) obj;
        System.out.println(friend.getFirstName());
    }
}
