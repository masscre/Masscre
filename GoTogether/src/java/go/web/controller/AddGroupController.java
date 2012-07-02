package go.web.controller;

import go.dto.Database;
import go.web.model.Group;
import go.web.model.Login;
import go.web.model.Ride;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddGroupController extends SimpleFormController {
        @Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		             
		Group tempGroup = (Group) command;
                HttpSession session = request.getSession(true);
                Login user = (Login) session.getAttribute("user");        
                Database.database.addGroupToUser(user.getUser().getId().toString(), tempGroup);                
                ModelAndView modelAndView = new ModelAndView("redirect:groups.htm");			
		return modelAndView;		
        }
}   

