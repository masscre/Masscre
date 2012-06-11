package go.web.model;

public class Registration {
    public Registration(){}
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String passwordcheck;
    private String email;
    
    public String getFirstname() {
        return firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPasswordcheck() {
        return passwordcheck;
    }

    public String getEmail() {
        return email;
    }
    
    
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPasswordcheck(String passwordcheck) {
        this.passwordcheck = passwordcheck;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
