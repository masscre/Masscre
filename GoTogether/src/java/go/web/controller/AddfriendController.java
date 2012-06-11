package go.web.controller;

import go.dto.Database;
import go.web.model.Friend;

import go.web.model.User;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("deprecation")
public class AddfriendController extends SimpleFormController {
    
    
    
    @Override
	protected ModelAndView onSubmit(Object command) throws ServletException {
		Friend friend = (Friend) command; 
                             
                
                ArrayList<String> byFirstNameIds = new ArrayList();
                ArrayList<String> byLastNameIds = new ArrayList();
                ArrayList<String> byEmailIds = new ArrayList();
                
                String ids = "?ids=";
                
                ArrayList<User> byFirstName = new ArrayList();
                if (!"".equals(friend.getFirstName())) {                    
                    byFirstName = Database.database.getUsersByFirstName(friend.getFirstName());
                    Iterator it = byFirstName.iterator();
                    while(it.hasNext()) {
                        User u = (User) it.next();
                        if (u != null) byFirstNameIds.add(u.getId().toString());                        
                    }                      
                }
                
                ArrayList<User> byLastName = new ArrayList();
                if (!"".equals(friend.getLastName())) {
                    byLastName = Database.database.getUsersByLastName(friend.getLastName());
                    Iterator it = byLastName.iterator();
                    while(it.hasNext()) {
                        User u = (User) it.next();
                        if (u != null) byLastNameIds.add(u.getId().toString());
                    }
                }
                
                ArrayList<User> byEmail = new ArrayList();
                if (!"".equals(friend.getEmail())) {
                    byEmail = Database.database.getUsersByEmail(friend.getEmail());
                    Iterator it = byEmail.iterator();
                    while(it.hasNext()) {
                        User u = (User) it.next();
                        if (u != null) byEmailIds.add(u.getId().toString());
                    }
                }
                
                ids += filter(byFirstNameIds, byLastNameIds, byEmailIds);
                
		ModelAndView modelAndView = new ModelAndView("redirect:addfriendlist.htm"+ids);
                
		return modelAndView;		
    }
    
    private String filter(ArrayList byFirstName, ArrayList byLastName, ArrayList byEmail) {
        String result = "";
        
        if (!byFirstName.isEmpty() && byLastName.isEmpty() && byEmail.isEmpty()) {
            Iterator it = byFirstName.iterator();
            while(it.hasNext()) {
                result += it.next()+",";
            }
        }            
        
        if (byFirstName.isEmpty() && !byLastName.isEmpty() && byEmail.isEmpty()) {
            Iterator it = byLastName.iterator();
            while(it.hasNext()) {
                result += it.next()+",";
            }
        }
        
        if (byFirstName.isEmpty() && byLastName.isEmpty() && !byEmail.isEmpty()) {
            Iterator it = byEmail.iterator();
            while(it.hasNext()) {
                result += it.next()+",";
            }
        }
        
        if (!byFirstName.isEmpty() && !byLastName.isEmpty() && byEmail.isEmpty()) {
            Iterator it = byFirstName.iterator();
            while(it.hasNext()) {
                String firstName = (String) it.next();
                Iterator it2 = byLastName.iterator();
                while(it2.hasNext()) {
                    String lastName = (String) it2.next();
                    if (firstName.equals(lastName)) result += firstName+",";
                }
            }
        } 
        
        return result;
    }
}
