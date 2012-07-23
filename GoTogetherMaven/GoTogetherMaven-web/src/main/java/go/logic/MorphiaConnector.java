package go.logic;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import go.model.Message;
import go.model.Ride;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class MorphiaConnector {
    
    DatabaseConnector db; 
    Mongo mongo;
    Morphia morphia;
    Datastore ds;
    
    public MorphiaConnector() {
        db = new DatabaseConnector();
        mongo = db.getMongo();
        morphia = new Morphia();
        morphia.map(Message.class);
        ds = morphia.createDatastore("gotogether");
    }  
    
    public void deleteMessage(String id) {
        ds.delete(Message.class, new ObjectId(id));
    }
    
    public void saveMessage(Message message) {
        ds.save(message);
    }
    
    public ArrayList<Message> getRideMessages(String rideId) {
        ArrayList<Message> messages = (ArrayList<Message>) ds.find(Message.class, "rideId ==", rideId).asList();
        return messages;
    }
    
}
