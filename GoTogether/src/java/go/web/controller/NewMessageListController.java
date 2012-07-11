/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author tomasmalich
 */
public class NewMessageListController extends AbstractController{
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        Login login;
        HttpSession session;
        try {
            session = hsr.getSession();
            login = (Login)session.getAttribute("user"); 
            if (!login.loggedIn()) return new ModelAndView("redirect:index.htm");
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }                      
        
        ArrayList friendsIds = new ArrayList();
        ArrayList<User> friends = new ArrayList();
        try {
           friendsIds = (ArrayList) Database.database.getUserFriendsList(login.getUser().getId().toString()); 
           Iterator it = friendsIds.iterator();
           while(it.hasNext()) {
               User u = null;
               try {
                   u = Database.database.getUser((String)it.next());
               } catch (Exception e) {
                   System.out.println(e);
               }
               friends.add(u);
           }
           session.setAttribute("friends", friends);
        } catch (Exception e) {
            System.out.println("Nelze dostat seznam pratel");            
        }
        
        String firstName = login.getUser().getFirstname();
        String lastName = login.getUser().getLastname(); 
        
        return new ModelAndView("newmessagelist", "name", firstName+" "+lastName);
    }
    
}
