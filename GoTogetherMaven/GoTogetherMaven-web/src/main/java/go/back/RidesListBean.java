package go.back;

import go.model.Ride;
import go.model.User;
import java.io.IOException;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RidesListBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public RidesListBean() {        
    }
    
    private ArrayList<Ride> ridesList = new ArrayList();

    public ArrayList<Ride> getRidesList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String thisUserName = facesContext.getExternalContext().getRemoteUser();
        ridesList = databaseBean.getRidesList(thisUserName);        
        return ridesList;
    }
    
    public boolean isRidesListEmpty() {
        getRidesList();
        if (ridesList.isEmpty()) return true;
        else return false;
    }
    
}
