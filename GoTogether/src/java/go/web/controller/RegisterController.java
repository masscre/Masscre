package go.web.controller;

import go.web.model.Registration;
import javax.servlet.ServletException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("deprecation")
public class RegisterController extends SimpleFormController {
    @Override
	protected ModelAndView onSubmit(Object command) throws ServletException {
		Registration registration = (Registration) command;               
		ModelAndView modelAndView = new ModelAndView("redirect:registration_successfull.htm");			
		return modelAndView;		
    }
}
