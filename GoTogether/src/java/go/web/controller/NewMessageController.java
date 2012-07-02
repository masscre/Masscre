package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.Message;
import go.web.model.Ride;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class NewMessageController extends SimpleFormController {
        @Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException, IOException {		             
		Message tempMessage = (Message) command;
                HttpSession session = request.getSession(true);
                Login user = (Login) session.getAttribute("user");   
                tempMessage.setFrom(user.getUser().getId().toString());
                tempMessage.setTo(request.getParameter("id"));
                tempMessage.setSendDate(Message.getDateTime());
                tempMessage.setId();
                Database.database.sendMessage(tempMessage);
                ModelAndView modelAndView = new ModelAndView("redirect:inbox.htm");			
		return modelAndView;		
        }
}   

