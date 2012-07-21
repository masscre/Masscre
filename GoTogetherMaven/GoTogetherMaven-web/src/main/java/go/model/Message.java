package go.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;

@Entity
public class Message {
    
    @Id private ObjectId id;
    private String author;
    private String message;
    private String rideId;
    
    public Message(String author, String message) {
        this.author = author;
        this.message = message;
    }
    
    public Message() {
        
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getRideId() {
        return rideId;
    }   

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }
    
    
    
}
