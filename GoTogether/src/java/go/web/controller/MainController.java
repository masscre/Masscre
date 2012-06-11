package go.web.controller;

import go.web.model.Login;
import go.web.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class MainController extends AbstractController{

    Login login;
    User user; 
    
    String firstName;
    String lastName;
    
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
        firstName = user.getFirstname();
        lastName = user.getLastname();  
        
        if (request == true) {
            session.setAttribute("requests", "<h4>Requests: </h4>");
        } else {
            session.setAttribute("requests", "");
        }
        
        return new ModelAndView("main", "name", firstName+" "+lastName);
    }
    
    
}
