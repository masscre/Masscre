package go.back;

import go.logic.MorphiaConnector;
import go.model.Message;
import go.model.Ride;
import go.model.User;
import java.io.IOException;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RideDetailBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
    private Ride ride;
    private ArrayList<Message> messages = new ArrayList();
    private String message;
    private String rideId;
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public RideDetailBean() { 
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        rideId = paramMap.get("id");
        System.out.println(rideId);        
    }
    
    public Ride getRide() {
        ride = databaseBean.getRide(rideId);
        return ride;
    }
    
    public ArrayList<Message> getMessages() {
        messages = databaseBean.getRideMessages(rideId);
        return this.messages;
    }
    
    public void addMessage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();        
        String name = facesContext.getExternalContext().getRemoteUser();
        User autor = databaseBean.getUser(name);
        Message m = new Message(autor.getUserFullName(), message);        
        System.out.println(ride.getId());
        databaseBean.addMessageToRide(m, ride.getId());        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
