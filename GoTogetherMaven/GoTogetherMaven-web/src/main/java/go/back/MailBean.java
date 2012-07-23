package go.back;

import go.model.Mail;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class MailBean {
    
    private ArrayList<Mail> mails;
    
    public MailBean() {
    }

    public ArrayList<Mail> getMails() {
        if (mails == null) mails = new ArrayList();
        mails.add(new Mail());
        return mails;
    }

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }
    
    
    
    
}
