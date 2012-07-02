package go.web.validator;


import go.dto.Database;
import go.web.model.Friend;
import go.web.model.Message;
import go.web.model.User;
import java.util.ArrayList;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class NewMessageValidator implements Validator {
    
    @Override
    public boolean supports(Class clazz) {
        return Message.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
        Message message = (Message) obj;        
    }
}
