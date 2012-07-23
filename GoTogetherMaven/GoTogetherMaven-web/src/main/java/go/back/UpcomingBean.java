package go.back;

import go.model.Ride;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UpcomingBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public UpcomingBean() {        
    }

    private ArrayList<Ride> ridesList = new ArrayList();

    public ArrayList<Ride> getRidesList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String thisUserName = facesContext.getExternalContext().getRemoteUser();
        ridesList = databaseBean.getUpcomingList(thisUserName);        
        return ridesList;
    }
    
    public boolean isRidesListEmpty() {
        getRidesList();
        if (ridesList.isEmpty()) return true;
        else return false;
    }

}
