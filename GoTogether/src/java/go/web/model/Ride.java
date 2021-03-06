package go.web.model;

import com.mongodb.BasicDBObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Ride {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String from;
    private String to;
    private String id;
    private String ownerName;
    
    public Ride(){}
        
    public String getHourVisual() {
        if (hour < 10) return "0"+hour;
        return ""+hour;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getMinuteVisual() {
        if (minute < 10) return "0"+minute;
        return ""+minute;
    }
    
    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }    
    
    public void setId(String id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }    
    
    public String getId() {
        return id;
    }
    
}
