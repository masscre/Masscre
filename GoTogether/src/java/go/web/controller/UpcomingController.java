/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author tomasmalich
 */
public class UpcomingController extends AbstractController{
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        
        ArrayList rides = new ArrayList();
        HttpSession session;
        Login login;
        
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }      
              
        try {
           rides = Database.database.getUpcomingRides(login.getUser().getId().toString()); 
           session.setAttribute("rides", rides);
        } catch (Exception e) {
            System.out.println(e);            
        }
        
        String firstName = login.getUser().getFirstname();
        String lastName = login.getUser().getLastname();  
        
        return new ModelAndView("upcoming" , "name", firstName+" "+lastName);
    }
    
}
