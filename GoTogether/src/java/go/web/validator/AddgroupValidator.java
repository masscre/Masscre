/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.validator;

import go.dto.Database;
import go.web.model.Group;
import go.web.model.Ride;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class AddgroupValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Group.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Group tempGroup = (Group) obj;   
        if (tempGroup.getGroupName().isEmpty()) {
            errors.rejectValue("groupName", "error.empty.field", "Please insert group name.");
        }
    }
}
