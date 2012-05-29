package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class AdministrationController extends AbstractController{
   
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        ArrayList users = new ArrayList();
        HttpSession session;
        Login login;
        
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }
        
        
           users = Database.database.getUsers(); 
           session.setAttribute("users", users);
        
        
        return new ModelAndView("administration");
    }
}
