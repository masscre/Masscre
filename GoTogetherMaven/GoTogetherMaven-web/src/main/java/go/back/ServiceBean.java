package go.back;

import go.model.User;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@Singleton
public class ServiceBean implements Serializable {
        
    public ServiceBean() {  
        System.out.println("Starting service");
        go.service.ActiveAccountsPublisher.main(new String[0]);
    }
    

}
