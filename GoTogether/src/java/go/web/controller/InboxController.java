/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.Message;
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
public class InboxController extends AbstractController{
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        Login login;
        HttpSession session;
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }                      
        
        try {
            ArrayList<Message> messages = Database.database.getUserMessages(login.getUser().getId().toString());            
            session.setAttribute("messages", messages);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        String firstName = login.getUser().getFirstname();
        String lastName = login.getUser().getLastname(); 
        
        return new ModelAndView("inbox", "name", firstName+" "+lastName);
    }
    
}
