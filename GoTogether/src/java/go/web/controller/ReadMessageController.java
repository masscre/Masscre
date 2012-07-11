/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.Message;
import go.web.model.Ride;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ReadMessageController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {     
        
        String subject;
        String message;
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
            Message m = Database.database.readMessage(login.getUser().getId().toString(), hsr.getParameter("id"));
            session.setAttribute("subject", m.getSubject());
            session.setAttribute("message", m.getMessage());   
            session.setAttribute("from", m.getFrom());
            session.setAttribute("messageId", m.getId());
        } catch (Exception e) {
            System.out.println(e);            
        }
        
        String firstName = login.getUser().getFirstname();
        String lastName = login.getUser().getLastname();
        
        return new ModelAndView("readmessage", "name", firstName+" "+lastName);
    }    
}
