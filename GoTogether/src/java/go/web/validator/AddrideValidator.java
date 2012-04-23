/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.validator;

import go.web.model.Database;
import go.web.model.Ride;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Masscre
 */
public class AddrideValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Ride.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Ride tempRide = (Ride) obj;   
        if (tempRide.getDay() <= 0 || tempRide.getDay() > 31) {
            errors.rejectValue("day", "error.empty.field", "Day format is wrong."); 
        }
        if (tempRide.getMonth() <= 0 || tempRide.getMonth() > 12) {
            errors.rejectValue("month", "error.empty.field", "Month format is wrong.");
        }
        if (tempRide.getYear() < 2012 || tempRide.getYear() > 2100) {
            errors.rejectValue("year", "error.empty.field", "Year format is wrong.");
        }
        if (tempRide.getHour() < 0 || tempRide.getHour() > 24) {
            errors.rejectValue("hour", "error.empty.field", "Hours format is wrong.");
        }
        if (tempRide.getMinute() < 0 || tempRide.getMinute() > 59) {
            errors.rejectValue("minute", "error.empty.field", "Minutes format is wrong.");
        }
        System.out.println("New ride "+tempRide.getDay()+"/"+tempRide.getMonth()+"/"+tempRide.getYear()+" "+tempRide.getHour()+":"+tempRide.getMinute()+
                " From "+tempRide.getFrom()+" To "+tempRide.getTo());
    }
}
