package go.web.controller;

import go.web.model.Login;
import go.web.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class MainController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        try {
            HttpSession session = hsr.getSession();
            Login login = (Login)session.getAttribute("user");  
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }                      
        return new ModelAndView("main");
    }
    
}
