package go.web.model;

import go.dto.Database;

public class Login {

    public Login(){}
    
    private String username;
    private String password;    
    private int rights;
    private User user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void login() {
        this.user = Database.database.login(this.username, this.password);
    }
    
    public boolean loggedIn() {
        return user != null;
    }
    
    public User getUser() {
        return this.user;
    }
    
}
