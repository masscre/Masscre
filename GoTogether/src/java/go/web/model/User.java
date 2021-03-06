package go.web.model;

import go.dto.Database;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.bson.types.ObjectId;

public class User {

    private String id;
    private String firstname;
    private String lastname;    
    private String username;
    private String email;
    private int rights;
    private boolean logged;
    private List<Group> groups;
    private ArrayList<Ride> rides;
    private List<Ride> onBoardOn;
    
    public User(String username, int rights, String id, String firstName, String lastName) {
        this.username = username;
        this.rights = rights;
        this.firstname = firstName;
        this.lastname = lastName;
        this.logged = true; 
        this.id = id;
    }
    
    public User(String firstname, String lastname, String username, String email, String id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
    }
    
    public User(){}

    public ObjectId getId() {
        return Database.database.getUserId(username);
    }       
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isLogged() {
        return logged;
    }

    public int getRights() {
        return rights;
    }

    public String getUsername() {
        return username;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Ride> getOnBoardOn() {
        return onBoardOn;
    }

    public ArrayList<Ride> getRides() {
        return rides;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void setOnBoardOn(List<Ride> onBoardOn) {
        this.onBoardOn = onBoardOn;
    }

    public void setRides(ArrayList<Ride> rides) {
        this.rides = rides;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public void setUsername(String username) {
        this.username = username;
    }       
    
    public static UserComparator getUserComparator() {
        return new UserComparator();
    }
    
}


class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getUsername().compareTo(o2.getUsername());
    }
}
