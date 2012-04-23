package go.web.model;

import java.util.List;

public class Group {
    
    private List<User> members;
    private User owner;
    private String groupName;
    private int visibility;

    public String getGroupName() {
        return groupName;
    }

    public List<User> getMembers() {
        return members;
    }

    public User getOwner() {
        return owner;
    }

    public int getVisibility() {
        return visibility;
    }
    
    
    
    
}
