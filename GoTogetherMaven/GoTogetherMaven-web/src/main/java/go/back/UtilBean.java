package go.back;

import go.model.User;
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
public class UtilBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;


    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public UtilBean() {        
    }
    

}
