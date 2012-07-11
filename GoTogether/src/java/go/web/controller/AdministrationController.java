package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
           Collections.sort(users, User.getUserComparator());
           session.setAttribute("users", users);
        
           String firstName = login.getUser().getFirstname();
        String lastName = login.getUser().getLastname(); 
        
        
        return new ModelAndView("administration", "name", firstName+" "+lastName);
    }
}

