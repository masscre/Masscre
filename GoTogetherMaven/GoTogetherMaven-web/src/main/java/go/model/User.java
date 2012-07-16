package go.model;

import go.utils.StringUtils;

public class User {
    
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    
    public User() {}
    
    public User(String userName, String firstName, String lastName, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public String getUserFullName() {
        return StringUtils.makeFirstCharUpperCase(firstName)+" "+StringUtils.makeFirstCharUpperCase(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }   

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
