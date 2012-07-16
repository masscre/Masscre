package go.setting;

public class DatabaseSetting {
    
    public static DatabaseSetting databaseSetting = new DatabaseSetting();
    
    public DatabaseSetting() {
        // default constructor
        this.hostname = "localhost";
    }
    
    private String hostname;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }   
    
}
