package go.back;

import go.model.User;
import java.io.IOException;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class FriendsListBean implements Serializable {
    
    @ManagedProperty(value="#{databaseBean}")
    private DatabaseBean databaseBean;
    
   
    public void setDatabaseBean(DatabaseBean databaseBean) {
            this.databaseBean = databaseBean;
    }
    
    public FriendsListBean() {        
    }
    
    private String removeFriend = "";

    private ArrayList<User> friendsList = new ArrayList();
    private ArrayList<User> friendsRequestsList = new ArrayList();

    public ArrayList<User> getFriendsList() {
        friendsList = databaseBean.getFriendsList();        
        return friendsList;
    }
    
    public boolean isFriendsListEmpty() {
        getFriendsList();
        if (friendsList.isEmpty()) return true;
        else return false;
    }
    
    public ArrayList<User> getFriendsRequestsList() {        
        friendsRequestsList = databaseBean.getFriendsRequests();
        return friendsRequestsList;
    }
    
    public boolean isFriendsRequestsListEmpty() {
        getFriendsRequestsList();
        if (friendsRequestsList == null) return true;
        return friendsRequestsList.isEmpty();
    }	
    
    public void declineFriend(String userName) {        
        databaseBean.declineFriend(userName);
    }
    
    public void confirmFriend(String userName) {
        databaseBean.confirmFriend(userName);
    }
    
    public void removeFriend(String userName) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("rmfriendok.xhtml?id="+userName);
        removeFriend = userName;
    }
    
    public void removeFriendConfirm() throws IOException {
        databaseBean.removeFriend(removeFriend);
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

}
