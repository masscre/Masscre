package go.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.types.ObjectId;

@Entity
public class Mail {
    
    @Id private ObjectId id;
    private String from;
    private String to;
    private String subject;
    private String message;
    private String sendDate;
    private Boolean readed;
    
    public Mail() {
        
    }
    
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getFrom() {
        return from;
    }

    public ObjectId getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getReaded() {
        return readed;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    
    
}
