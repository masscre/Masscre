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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class AddFriendBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;


    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public AddFriendBean() {        
    }

    private ArrayList<User> searchList = new ArrayList();

    
    public boolean isSearchListEmpty() {
        if (searchList == null) return true;
        return searchList.isEmpty();
    }

    public ArrayList<User> getSearchList() {
        return searchList;
    }

    public void find() {
        String firstname = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("firstname");
        String lastname = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("lastname");
        try {
            searchList = databaseBean.searchUsers(firstname, lastname);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String addFriend() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String userName= paramMap.get("id");
        User u = databaseBean.getUser(userName);  
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String name = facesContext.getExternalContext().getRemoteUser();
        databaseBean.sendFriendRequest(userName, name);
        return u.getUserFullName();
    }

}
