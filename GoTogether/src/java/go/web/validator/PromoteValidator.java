/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.validator;

import go.dto.Database;
import go.web.model.Promote;
import go.web.model.Ride;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class PromoteValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Promote.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Promote tempPromote = (Promote) obj;   
        if (tempPromote.getLevel() < 0 || tempPromote.getLevel() > 3) {
            errors.rejectValue("level", "error.empty.field", "Level format is wrong. Posible values are 1, 2, 3"); 
        }
    }
}
