package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.Promote;
import go.web.model.Ride;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class PromoteController extends SimpleFormController {
        
        @Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		             
		Promote tempPromote = (Promote) command;
                HttpSession session = request.getSession(true);                
                ModelAndView modelAndView = new ModelAndView("redirect:administration.htm");  
                                
                Login login;

                try {                    
                    login = (Login)session.getAttribute("user"); 
                    if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
                } catch (Exception e) {
                    return new ModelAndView("redirect:index.htm");
                }
                
                if (login.loggedIn() && login.getUser().getRights() == 3) {
                    Database.database.promoteUser(request.getParameter("id"), tempPromote.getLevel());
                }
                
		return modelAndView;		
        }
}   

