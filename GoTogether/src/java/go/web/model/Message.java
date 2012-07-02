package go.web.model;

import go.dto.Database;
import go.utils.Hash;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Message implements Serializable{
    
    private String message;
    private String subject;
    private String from;
    private String to;
    private boolean readed = false;
    private String sendDate;
    private String id;
    
    public Message() {}
    
    public String getSenderInfo() {
        User user = Database.database.getUser(from);
        String info = user.getFirstname()+" "+user.getLastname();
        return info;
    }
    
    public byte[] Serialize() throws IOException {
        ObjectOutput out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        out = new ObjectOutputStream(bos) ;
        out.writeObject(this);
        out.close();
        byte[] buf = bos.toByteArray();
        return buf;
    }
    
    public static Message Deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in;
        in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) in.readObject();        
        in.close();        
        return message;
    }
    
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    Message(String from, String to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.sendDate = getDateTime();
    }
    
    public void sendMessage() throws IOException {
        Database.database.sendMessage(this);
    }
    
    public void readMessage() {
        this.readed = true;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReaded() {
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

    public String getId() {
        return id;
    }   
    

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReaded(boolean readed) {
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
    
    public void setId() {
        this.id = Hash.hashToSha(sendDate+from);
    }
    
    
    
}


    