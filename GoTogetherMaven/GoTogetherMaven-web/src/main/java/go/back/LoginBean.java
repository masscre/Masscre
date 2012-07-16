package go.back;

import java.io.IOException;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {
    
    
    public LoginBean() {
    }

    public boolean isLoggedAdministrator() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("UserAdministrator");
    }

    public String getLoginName() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (p == null) {return null;}
        return p.getName();
    }

    public String logout() throws IOException {
        HttpSession sess = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sess.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        return "index";
    }
    
}
