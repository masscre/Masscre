package go.model;

import java.util.ArrayList;
import java.util.Date;

public class Ride {
    
    public Ride() {
        
    }

    public Ride(String from, String to, String date, String time, int numberOfSeats, String taxType) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.numberOfSeats = numberOfSeats;
        this.taxType = taxType;
    }   
    
    private String from;
    private String to;
    private String date;
    private String time;
    private String id;
    private int numberOfSeats;
    private String taxType;
    private ArrayList<User> coriders = new ArrayList();
    private String owner;
    
    public int getUsedSeats() {
        return coriders.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }   

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getTaxType() {
        return taxType;
    }

    public String getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }

    public ArrayList<User> getCoriders() {
        return coriders;
    }

    public String getOwner() {
        return owner;
    }
    
    

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCoriders(ArrayList<User> coriders) {
        this.coriders = coriders;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    
    
    
    
}
