package go.web.controller;

import go.dto.Database;
import go.web.model.Login;
import go.web.model.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        
        session.removeAttribute("requests");
        session.removeAttribute("requestTable");
        
        List requestsList = null;
        try {
            requestsList = Database.database.getUserFriendsRequests(user.getId().toString());              
        } catch (Exception e) {             
        }
        
        if (!requestsList.isEmpty()) request = true;        
        
        if (request == true) {            
            session.setAttribute("requests", "<h1 class=\"table_title\">Friends requests: </h1>");
            String requestsTable = "<table width=\"600px\" border=\"1\" bgcolor=\"0DD939\">";
            requestsTable += "<tr><td>Firstname</td><td>Lastname</td><td>Reject</td><td>Accept</td></tr>";
            try {  
                Iterator it = requestsList.iterator();
                while(it.hasNext()) {
                    User u = Database.database.getUser((String)it.next());                    
                    requestsTable += "<tr><td>"+u.getFirstname()+"</td><td>"+u.getLastname()+"</td>"
                            + "<td><a href=\"rejectfriend.htm?id="+u.getId().toString()+"\">reject</a></td>"
                            + "<td><a href=\"confirmfriend.htm?id="+u.getId().toString()+"\">accept</tr>";
                }                                         
                requestsTable += "</table>";
                session.setAttribute("requestsTable", requestsTable);
            } catch (Exception e) {}
        } else {
            session.setAttribute("requests", "");
        }
        
        try {
            String friendList = "";
            List friendsList = Database.database.getUserFriendsList(user.getId().toString());
            Iterator it = friendsList.iterator();
            while(it.hasNext()) {
                User u = Database.database.getUser((String)it.next());                
                friendList += "<tr><td>"+u.getFirstname()+"</td><td>"+u.getLastname()+
                        "<td>"+"-"+"</td>"+"<td>"+"<a href=\"newmessage.htm?id="+u.getId().toString()+"\"> message </a>"
                        +"</td>"+"<td><a href=\"editfriend.htm?id="+u.getId().toString()+"\">edit</a></td>"+"</tr>";
            }
            session.setAttribute("friendsList", friendList);
        } catch(Exception e) {
            
        }     
        
        
        return new ModelAndView("main", "name", firstName+" "+lastName);
    }
    
    
}
