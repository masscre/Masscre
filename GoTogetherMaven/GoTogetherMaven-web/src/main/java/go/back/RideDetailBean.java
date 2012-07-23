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
@RequestScoped
public class RideDetailBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
    @ManagedProperty(value="#{sessionBean}")
    private SessionBean sessionBean;
    
    private Ride ride;
    private ArrayList<Message> messages = new ArrayList();
    private String message;
    private String rideId;
    private String emptyMessage = "";
    private ArrayList<User> requests = new ArrayList();
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public void setSessionBean(SessionBean sessionBean) {
            this.sessionBean = sessionBean;
    }
    
    public RideDetailBean() {
    }
    
    public Ride getRide() {
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
        databaseBean.addMessageToRide(m, ride.getId()); 
        this.message = "";
    }

    public ArrayList<User> getRequests() {
        requests = databaseBean.getRideRequests(rideId);
        return requests;
    }

    public void setRequests(ArrayList<User> requests) {
        this.requests = requests;
    }
    
    public void deleteMessage(String id) {
        databaseBean.deleteMessage(id);
    }

    public String getMessage() {
        return message;
    }
    
    public String getEmptyMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOwner() {
        rideId = sessionBean.getRideId();
        ride = databaseBean.getRide(rideId);
        FacesContext facesContext = FacesContext.getCurrentInstance();        
        String name = facesContext.getExternalContext().getRemoteUser();
        String owner = ride.getOwnerUserName();
        return owner.equals(name);
    }
    
    public void requestSeat() {
        FacesContext facesContext = FacesContext.getCurrentInstance();        
        String name = facesContext.getExternalContext().getRemoteUser();
        databaseBean.requestSeat(name, ride.getId());
    }   
    
    public boolean anyRequests() {
        return !ride.getRequests().isEmpty();
    }
    
    public void declineRequest(String userName) {
        databaseBean.declineRequest(userName, ride.getId());
    }
    
    public void acceptRequest(String userName) {
        databaseBean.acceptRequest(userName, ride.getId());
    }
    
    public void deleteRide() throws IOException {
        databaseBean.deleteRide(ride.getId());
        FacesContext.getCurrentInstance().getExternalContext().redirect("myrides.xhtml");
    }
    
}
