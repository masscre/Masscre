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


public class AddFriendListController extends AbstractController{

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
        
        String table = "";
        String parameter = hsr.getParameter("ids");
        String[] split = parameter.split(",");
                  
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() == 24) {
                User u = Database.database.getUser(split[i]);
                table += "<tr><td>"+u.getFirstname()+"</td><td>"+u.getLastname()+"</td><td>"+u.getEmail()+"</td><td><a href=\"addfriendconfirm.htm?id="+u.getId().toString()+"\"><img src=\"img/plus_button.jpg\"/></td></tr>";                
            }
        }
         
        
        session.setAttribute("table", table);
        
        return new ModelAndView("addfriendlist");
    }
    
    
}
