package go.web.model;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
    
    private List<String> members;
    private String owner;
    private String groupName;
    private int visibility;
    private String id;

    public String getGroupName() {
        return groupName;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getOwner() {
        return owner;
    }

    public int getVisibility() {
        return visibility;
    }

    public String getId() {
        return id;
    }
    

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
    
}
