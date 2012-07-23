package go.back;

import go.model.Mail;
import go.model.User;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class UtilBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
    
    @ManagedProperty(value="#{mailBean}")
    private MailBean mailBean;


    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public void setMailBean(MailBean mailBean) {
            this.mailBean = mailBean;
    }
    
    public UtilBean() {        
    }
    
    public String thisPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getViewRoot().getViewId();
    }
    
    public boolean isIndex() {        
        return thisPage().equals("/index.xhtml");
    }
    
    public boolean isRides() {        
        return thisPage().equals("/myrides.xhtml");
    }
    
    public boolean isAddRide() {
        return thisPage().equals("/addride.xhtml");
    }
    
    public boolean isRideDetail() {
        return thisPage().equals("/ridedetail.xhtml");
    }
    
    public boolean isUpcoming() {        
        return thisPage().equals("/upcoming.xhtml");
    }
    
    public boolean isManagement() {        
        return thisPage().equals("/management.xhtml");
    }
    
    public boolean isAdministration() {        
        return thisPage().equals("/administration.xhtml");
    }
    
    public String getDate() {
        return getDateUtil().toString();        
    }
    
    public String getTime() {
        return getTimeUtil().toString();
    }
    
    public static String getDateUtil() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static String getTimeUtil() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public boolean newMail() {
        ArrayList<Mail> mails = mailBean.getMails();
        if (mails == null) return false;
        if (mails.isEmpty()) return false;
        Iterator it = mails.iterator();
        while(it.hasNext()) {
            Mail m = (Mail) it.next();
            if (m.getReaded()) return false;
        }
        return true;
    }
    
    

}
