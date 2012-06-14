package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.User;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class AddFriendConfirmController extends AbstractController{

    Login login;
    User user; 
        
    boolean request = false;
       
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        HttpSession session;
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user");  
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
            else {
                user = login.getUser();
                if (user.getRights() == 3) session.setAttribute("adm", "<li><a href=\"administration.htm\">Administration</a></li>");
                else session.setAttribute("adm", "");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }
        String attribute = hsr.getParameter("id");
        try {            
            if (!attribute.equals(user.getId().toString())) Database.database.addFriendRequestToUser(user.getId().toString(), attribute);
        } catch (Exception e) {}
        
        return new ModelAndView("redirect:main.htm");
    }
    
    
}
