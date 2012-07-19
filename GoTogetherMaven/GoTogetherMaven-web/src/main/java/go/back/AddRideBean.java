package go.back;

import go.model.Ride;
import go.model.User;
import java.io.IOException;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class AddRideBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;


    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public AddRideBean() {        
    }
    
    public void addRide() throws IOException {
        String from = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("from");
        String to = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("to");
        DateFormat dateFormat = new SimpleDateFormat(FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("date"));
        DateFormat timeFormat = new SimpleDateFormat(FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("time"));
        String numberOfSeats = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("seats");
        String typeOfTax = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("tax");
        Date dateDate = new Date();
        String date = dateFormat.format(dateDate);
        Date timeDate = new Date();
        String time = timeFormat.format(timeDate);        
        Ride ride = new Ride(from, to, date, time, Integer.parseInt(numberOfSeats), typeOfTax);        
        databaseBean.addRide(ride);
        FacesContext.getCurrentInstance().getExternalContext().redirect("myrides.xhtml");
    }

}
