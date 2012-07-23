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
public class SessionBean implements Serializable {
    
    private String rideId;
    
    public SessionBean() { 
        
    }

    public String getRideId() {        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String tempId = null;
        try {
            tempId = paramMap.get("id");
        } catch (Exception e) {}
        if (tempId != null) rideId = tempId; 
        return rideId;
    }

    public void setRideId(String rideId) {         
        this.rideId = rideId;
    }
    
    
    
}

