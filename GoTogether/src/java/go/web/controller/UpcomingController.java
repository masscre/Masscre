/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import go.web.model.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author tomasmalich
 */
public class UpcomingController extends AbstractController{
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception { 
        try {
            HttpSession session = hsr.getSession();
            Login login = (Login)session.getAttribute("user");            
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }                      
        return new ModelAndView("upcoming");
    }
    
}
