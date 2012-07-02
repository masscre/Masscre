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

public class GroupRemoveController extends AbstractController{
   
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        HttpSession session; 
        Login login;
               
        
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");            
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }
        
        String groupName;
        try {
            groupName = Database.database.getGroupName(hsr.getParameter("id"));              
        } catch (Exception e){
            groupName = "neplatne jmeno";            
        }
        
        session.setAttribute("groupname", groupName);
        session.setAttribute("deleteId", hsr.getParameter("id"));
                
        return new ModelAndView("groupremove");
    }
}
