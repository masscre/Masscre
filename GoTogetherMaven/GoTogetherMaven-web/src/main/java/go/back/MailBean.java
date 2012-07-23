package go.back;

import go.model.Mail;
import go.model.User;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class MailBean {
    
    private ArrayList<Mail> mails;
    private Mail mail;
    private String message;
    private String subject;
    private String to;
    private String fullName;
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
    @ManagedProperty(value="#{sessionBean}")
    private SessionBean sessionBean;
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
        this.databaseBean = databaseBean;
    }
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    public MailBean() {
    }   
    
    public void update() {
        sessionBean.getMailTo();
    }
    
    public void updateMail() {
        mail = databaseBean.getMail(sessionBean.getMailId());
        fullName = getFullName(mail.getFrom());
    }
    
    public void readMail() {
        updateMail();
        databaseBean.readMail(mail.getId().toString());
    }
    
    public String getFullName(String userName) {
        User u = databaseBean.getUser(userName);
        return u.getUserFullName();
    }

    public ArrayList<Mail> getMails() {
        FacesContext context = FacesContext.getCurrentInstance();
        String userName = context.getExternalContext().getRemoteUser(); 
        mails = databaseBean.getMails(userName);
        if (mails == null) mails = new ArrayList();
        return mails;
    }

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }   
    
    public void sendMessage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        String userName = context.getExternalContext().getRemoteUser();        
        Mail mail = new Mail();
        mail.setFrom(userName);
        mail.setTo(sessionBean.getMailTo());
        mail.setSubject(subject);
        mail.setMessage(message);
        databaseBean.sendMail(mail);
        FacesContext.getCurrentInstance().getExternalContext().redirect("inbox.xhtml");
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }  
    
    public void deleteMail(String id) {
        databaseBean.deleteMail(id);
    }
    
    
}
