package go.web.model;

import java.util.List;

public class User {

    private String id;
    private String firstname;
    private String lastname;    
    private String username;
    private String email;
    private int rights;
    private boolean logged;
    private List<Group> groups;
    private List<Ride> rides;
    private List<Ride> onBoardOn;
    
    User(String username, int rights, String id) {
        this.username = username;
        this.rights = rights;
        this.logged = true; 
        this.id = id;
    }

    public String getId() {
        return id;
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

    public List<Ride> getRides() {
        return rides;
    }
    
    
    
}
