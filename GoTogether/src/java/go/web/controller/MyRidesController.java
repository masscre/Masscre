/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import go.web.model.Login;
import go.web.model.Ride;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author tomasmalich
 */
public class MyRidesController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {        
        try {
            HttpSession session = hsr.getSession();
            Login login = (Login)session.getAttribute("user");            
        } catch (Exception e) {
            return new ModelAndView("redirect:index.htm");
        }                      
        return new ModelAndView("myrides");
    }    
}
