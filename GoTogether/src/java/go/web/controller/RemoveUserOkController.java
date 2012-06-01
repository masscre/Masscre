package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.User;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class RemoveUserOkController extends AbstractController{
   
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        HttpSession session; 
        Login login;
        String id;    
        
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
            id = hsr.getParameter("id");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }
        
        if (login.loggedIn() && login.getUser().getRights() == 3) {
            Database.database.removeUser(id);
        }
                
        return new ModelAndView("redirect:administration.htm");
    }
}
