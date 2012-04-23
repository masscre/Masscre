/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Masscre
 */
public class LogoutController extends AbstractController {
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse respond) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.invalidate();                
        respond.reset();
        return new ModelAndView("redirect:index.htm");
    }
    
}
