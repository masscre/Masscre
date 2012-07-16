package go.back;

import com.mongodb.DBCollection;
import go.logic.DatabaseConnector;
import go.model.User;
import go.utils.StringUtils;
import go.utils.UserUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DatabaseBean implements Serializable {
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    public void setLoginBean(LoginBean loginBean) {
            this.loginBean = loginBean;
    }
    
    private DatabaseConnector dc;
    
    public DatabaseBean() {
        this.dc = new DatabaseConnector();
    }
    
    public User getUser(String userName) {
        return this.dc.getUserByUserName(userName);
    }
    
    public void registerUser(User u) {
        System.out.println("DatabaseBean: registering user "+u.getFirstName()+" "+u.getLastName());
        this.dc.registerUser(u);
    }
    
    public ArrayList<User> getFriendsList() {
        return UserUtils.formatUsers(dc.getUserFriendsList(loginBean.getLoginName()));
    }
    
    public ArrayList<User> searchUsers(String firstName, String lastName) {
        ArrayList<User> searchUsers = dc.searchUsers(firstName, lastName);
        ArrayList<User> updatedSearchUsers = new ArrayList();
        Iterator it = searchUsers.iterator();
        while(it.hasNext()) {
            User u = (User) it.next();
            u.setFirstName(StringUtils.makeFirstCharUpperCase(u.getFirstName()));
            u.setLastName(StringUtils.makeFirstCharUpperCase(u.getLastName()));            
            updatedSearchUsers.add(u);
        }
        return updatedSearchUsers;
    }
    
    public void sendFriendRequest(String userName, String friendName) {
        this.dc.addFriendRequest(userName, friendName);
    }
    
    public ArrayList<User> getFriendsRequests() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        return UserUtils.formatUsers(dc.getFriendsRequests(userName));
    }
    
    public void declineFriend(String userName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String thisUserName = facesContext.getExternalContext().getRemoteUser();
        dc.removeFriendRequest(thisUserName, userName);
    }
    
    public void confirmFriend(String userName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String thisUserName = facesContext.getExternalContext().getRemoteUser();
        dc.removeFriendRequest(thisUserName, userName);
        dc.addFriendToUser(thisUserName, userName);
        dc.addFriendToUser(userName, thisUserName);
    }
    
    
}
