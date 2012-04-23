package go.web.model;

import com.mongodb.*;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;


public class Database {
    private Mongo mongo;
    private DB db;
    private DBCollection users;
    private DBCollection rides;
    
    public static Database database = new Database();
    
    Database() {
        try {
            this.mongo = new Mongo("masscre.cz");
            this.db = mongo.getDB("gotogether");           
            this.users = db.getCollection("users");
            this.rides = db.getCollection("rides");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MongoException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void addRide(ObjectId userId, Ride ride) {
        BasicDBObject query = ride.getDbRide();
        query.put("userId", userId);
        rides.save(query);
        DBObject rideDb = rides.findOne(query);
        String rideId = rideDb.get("_id").toString();        
        BasicDBObject query2 = new BasicDBObject();
        query2.put("_id", userId);
        DBObject userDb = users.findOne(query2);        
        ArrayList ridesList = new ArrayList();
        if (userDb.get("rides") != null) {
            ridesList = (ArrayList)userDb.get("rides");
        }
        ridesList.add(rideId);
        userDb.put("rides", ridesList);
        users.save(userDb);
    }
    
    public ArrayList<Ride> getUserRides(ObjectId userId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", userId);
        DBObject user = users.findOne(query);
        ArrayList<String> ridesStringId = (ArrayList<String>) user.get("rides");
        Iterator it = ridesStringId.iterator();
        ArrayList<Ride> rideList = new ArrayList<Ride>();
        while(it.hasNext() == true) {
            String s = (String) it.next();
            ObjectId i = new ObjectId(s);
            BasicDBObject query2 = new BasicDBObject();
            query2.put("_id", i);
            DBObject rideDb = rides.findOne(query2);
            Ride r = new Ride();
            r.setDay(Integer.parseInt(rideDb.get("day").toString()));
            r.setMonth(Integer.parseInt(rideDb.get("month").toString()));
            r.setYear(Integer.parseInt(rideDb.get("year").toString()));
            r.setHour(Integer.parseInt(rideDb.get("hour").toString()));
            r.setMinute(Integer.parseInt(rideDb.get("minute").toString()));
            r.setFrom(rideDb.get("from").toString());
            r.setTo(rideDb.get("to").toString());
            rideList.add(r);            
        }
        return rideList;
    }
    
    public User login(String username, String password) {
        System.out.println("### DATABASE: finding user "+username);
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        query.put("password", password);
        DBObject user = users.findOne(query);
        if (user != null) {
            System.out.println("### DATABASE: user "+username+" found");
            User u = new User(user.get("username").toString(), 0, user.get("_id").toString());
            return u;
        } else {
            System.out.println("### DATABASE: user "+username+" not found");
            return null;
        }
    }
        
    public ObjectId getUserId(String username) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", username); 
        DBObject user = users.findOne(query);
        return (ObjectId)user.get("_id");
    }
    
    public boolean userExist(String username) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);        
        DBObject user = users.findOne(query);
        return user != null;
    }
    
    public void register(String firstname, String lastname, String username, String password) {
        String hashPassword = hashPassword(password);        
        BasicDBObject query = new BasicDBObject();
        query.put("firstname", firstname);
        query.put("lastname", lastname);
        query.put("username", username);
        query.put("password", password);
        users.save(query);
        System.out.println("### DATABASE: user "+username+" registered");
    }
    
    private String hashPassword(String password) {
        String hashword = "bdf0254521gjd$";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hashword;        
    }   
}
