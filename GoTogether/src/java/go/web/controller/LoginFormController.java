package go.web.controller;

import go.web.model.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.SimpleFormController;


@SuppressWarnings("deprecation")
public class LoginFormController extends SimpleFormController {
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		Login login = (Login) command;				
                HttpSession session = request.getSession(true);
                session.setAttribute("user", login);                   
		ModelAndView modelAndView = new ModelAndView("redirect:main.htm");
                modelAndView.addObject("id", login.getUser().getId());
		return modelAndView;		
        }        
       
}
