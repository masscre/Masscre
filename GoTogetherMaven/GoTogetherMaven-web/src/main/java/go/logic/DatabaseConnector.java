package go.logic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import go.model.Ride;
import go.model.User;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import go.setting.DatabaseSetting;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.types.ObjectId;

public class DatabaseConnector {
    
    private Mongo mongo;
    private DB db;
    private DBCollection users;
    private DBCollection rides;
    
    public DatabaseConnector() {
        try {
            this.mongo = new Mongo(DatabaseSetting.databaseSetting.getHostname());
            this.db = mongo.getDB("gotogether");
            this.users = db.getCollection("users");
            this.rides = db.getCollection("rides");
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DATABASE: No connection");
        } catch (MongoException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DATABASE: No connection");
        }
    }
    
    public Mongo getMongo() {
        return this.mongo;
    }
    
    public synchronized ArrayList<User> getUserFriendsList(String userName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> friendsList = new ArrayList();
        try {
            friendsList = (ArrayList<String>) user.get("friendsList");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (friendsList == null) friendsList = new ArrayList();
        Iterator it = friendsList.iterator();
        ArrayList<User> friendsListUser = new ArrayList();
        while(it.hasNext()) {
            User u = getUserByUserName((String)it.next());
            friendsListUser.add(u);
        }
        return friendsListUser;
    }
    
    public synchronized DBObject getUserDb(String userName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        return users.findOne(query);
    }
    
    public synchronized User getUserByUserName(String userName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject userDb = users.findOne(query);
        User user = new User();
        try {
            user.setUserName(userDb.get("username").toString());
            user.setFirstName(userDb.get("firstname").toString());
            user.setLastName(userDb.get("lastname").toString());
            user.setEmail(userDb.get("email").toString());
            try {
                user.setRides((ArrayList<String>)userDb.get("rides"));
            } catch (Exception e) {}
            if (user.getRides() == null) user.setRides(new ArrayList());
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }
    
    public synchronized void registerUser(User u) {
        BasicDBObject user = new BasicDBObject();
        String userName = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        try {
            userName = u.getUserName();
            userName = userName.toLowerCase();
        } catch (Exception e) {}
        try {
            firstName = u.getFirstName();
            firstName = firstName.toLowerCase();        
        } catch (Exception e) {}
        try {
            lastName = u.getLastName();
            lastName = lastName.toLowerCase();
        } catch (Exception e) {}
        try {
            email = u.getEmail();
            email = email.toLowerCase();
        } catch (Exception e) {}
        user.put("username", userName);
        user.put("firstname", firstName);
        user.put("lastname", lastName);
        user.put("email", email);
        users.save(user);
    }
   
    public synchronized ArrayList<User> searchUsers(String firstName, String lastName) {
        BasicDBObject query = new BasicDBObject();
        if (firstName.isEmpty() && lastName.isEmpty()) return null;
        if (!firstName.isEmpty()) query.put("firstname", firstName.toLowerCase());
        if (!lastName.isEmpty()) query.put("lastname", lastName.toLowerCase());
        DBCursor find = users.find(query);
        Iterator it = find.iterator();
        ArrayList<User> searchList = new ArrayList();
        while(it.hasNext()) {
            User u = new User();
            DBObject o = (DBObject) it.next();
            try {
                u.setUserName(o.get("username").toString());
            } catch (Exception e) {System.out.println(e);}
            try {
                u.setFirstName(o.get("firstname").toString());
            } catch (Exception e) {System.out.println(e);}
            try {
                u.setLastName(o.get("lastname").toString());
            } catch (Exception e) {System.out.println(e);}
            try {
                u.setEmail(o.get("email").toString());
            } catch (Exception e) {System.out.println(e);}
            searchList.add(u);
        }
        return searchList;
    }
    
    public synchronized void addFriendRequest(String userName, String friendName) {
        System.out.println("Friend request");
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> friendsRequests = new ArrayList();
        try {
            friendsRequests = (ArrayList<String>) user.get("friendsRequests");
        } catch (Exception e) {
            friendsRequests = new ArrayList();
        }
        if (friendsRequests == null) friendsRequests = new ArrayList();
        friendsRequests.add(friendName);
        user.put("friendsRequests", friendsRequests);
        users.save(user);
    }
    
    public synchronized ArrayList<User> getFriendsRequests(String userName) {
        DBObject user = getUserDb(userName);
        ArrayList<String> friendsRequests = new ArrayList();
        try {
            friendsRequests = (ArrayList<String>) user.get("friendsRequests");
        } catch (Exception e) {
            friendsRequests = new ArrayList();
        }
        if (friendsRequests == null) friendsRequests = new ArrayList();
        Iterator it = friendsRequests.iterator();
        ArrayList<User> requestsUsers = new ArrayList();
        while(it.hasNext()) {
            User u = getUserByUserName((String)(it.next()));
            requestsUsers.add(u);
        }
        return requestsUsers;
    }
    
    public synchronized void removeFriendRequest(String userName, String friendName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> friendsRequests = new ArrayList();
        try {
            friendsRequests = (ArrayList<String>) user.get("friendsRequests");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (friendsRequests == null) return;
        friendsRequests.remove(friendName);
        user.put("friendsRequests", friendsRequests);
        users.save(user);
    }
    
    public synchronized void addFriendToUser(String userName, String friendName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> friendsList = new ArrayList();
        try {
            friendsList = (ArrayList<String>) user.get("friendsList");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (friendsList == null) friendsList = new ArrayList();
        friendsList.add(friendName);
        user.put("friendsList", friendsList);
        users.save(user);
    }
    
    public int getNumberOfAccounts() {
        return (int) users.getCount();
    }
    
    public synchronized void removeFriend(String userName, String friendName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> friendsList = new ArrayList();
        try {
            friendsList = (ArrayList<String>) user.get("friendsList");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (friendsList == null) friendsList = new ArrayList();
        friendsList.remove(friendName);
        user.put("friendsList", friendsList);
        users.save(user);
    }
    
    public synchronized void addRide(Ride ride, String userName) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        User us = getUserByUserName(userName);
        BasicDBObject rideDb = new BasicDBObject();
        rideDb.put("from", ride.getFrom());
        rideDb.put("to", ride.getTo());
        rideDb.put("date", ride.getDate());
        rideDb.put("time", ride.getTime());
        rideDb.put("numberOfSeats", ride.getNumberOfSeats());
        rideDb.put("taxtype", ride.getTaxType()); 
        rideDb.put("ownerName", us.getUserFullName());
        rideDb.put("owner", userName);
        ArrayList<String> riders = new ArrayList();
        rideDb.put("coriders", riders);
        rides.save(rideDb);
        DBObject rideSaved = rides.findOne(rideDb);
        ArrayList<String> rides = new ArrayList();
        try {
            rides = (ArrayList<String>) user.get("rides");
        } catch (Exception e) {}
        if (rides == null) rides = new ArrayList();
        String rideId = rideSaved.get("_id").toString();
        rides.add(rideId);
        user.put("rides", rides);
        users.save(user);
    }
    
    public synchronized ArrayList<Ride> getRidesList(String userName) {        
        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        DBObject user = users.findOne(query);
        ArrayList<String> ridesIds = new ArrayList();
        try {
            ridesIds = (ArrayList<String>) user.get("rides");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (ridesIds == null) ridesIds = new ArrayList();
        Iterator it = ridesIds.iterator();
        ArrayList<Ride> ridesList = new ArrayList();
        while(it.hasNext()) {
            try {
                BasicDBObject query2 = new BasicDBObject();
                query2.put("_id", new ObjectId((String)it.next()));
                DBObject rideDb = rides.findOne(query2);
                Ride ride = new Ride();
                ride.setFrom(rideDb.get("from").toString());
                ride.setTo(rideDb.get("to").toString());
                ride.setDate(rideDb.get("date").toString());
                ride.setTime(rideDb.get("time").toString());
                ride.setNumberOfSeats(Integer.parseInt(rideDb.get("numberOfSeats").toString()));
                ride.setTaxType(rideDb.get("taxtype").toString());
                ride.setId(rideDb.get("_id").toString());
                ride.setOwner(rideDb.get("ownerName").toString());
                ArrayList<String> coridersIds = new ArrayList();
                try {
                    coridersIds = (ArrayList<String>) rideDb.get("coriders");
                } catch (Exception e) {}
                if (coridersIds == null) coridersIds = new ArrayList();
                Iterator it2 = coridersIds.iterator();
                ArrayList<User> usersInRide = new ArrayList();
                while(it2.hasNext()) {
                    User u = getUserByUserName((String)it2.next());
                    usersInRide.add(u);
                }
                ride.setCoriders(usersInRide);
                ridesList.add(ride);
            } catch (Exception e) {
                ridesList = new ArrayList();
            }
            if (ridesList == null) ridesList = new ArrayList();
        }       
        return ridesList;
    }
    
    public Ride getRide(String id) {
        System.out.println("Databaze "+id);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject rideDb = rides.findOne(query);
        Ride ride = new Ride();
        try {
            ride.setFrom(rideDb.get("from").toString());
                ride.setTo(rideDb.get("to").toString());
                ride.setDate(rideDb.get("date").toString());
                ride.setTime(rideDb.get("time").toString());
                ride.setNumberOfSeats(Integer.parseInt(rideDb.get("numberOfSeats").toString()));
                ride.setTaxType(rideDb.get("taxtype").toString());
                ride.setId(rideDb.get("_id").toString());
                ride.setOwner(rideDb.get("ownerName").toString());
                ride.setOwnerUserName(rideDb.get("owner").toString());
                ArrayList<String> coridersIds = new ArrayList();
                try {
                    coridersIds = (ArrayList<String>) rideDb.get("coriders");
                } catch (Exception e) {}
                if (coridersIds == null) coridersIds = new ArrayList();
                Iterator it2 = coridersIds.iterator();
                ArrayList<User> usersInRide = new ArrayList();
                while(it2.hasNext()) {
                    User u = getUserByUserName((String)it2.next());
                    usersInRide.add(u);
                }
                ride.setCoriders(usersInRide);
                ArrayList<String> requests = new ArrayList();
                try {
                    requests = (ArrayList<String>) rideDb.get("requests");
                } catch (Exception e) {}
                if (requests == null) requests = new ArrayList();
                ride.setRequests(requests);
        }   catch (Exception e) {
            System.out.println(e);
        }        
        if (ride == null) return new Ride();
        return ride;
    }
    
    public void addRequestToRide(String userName, String rideId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(rideId));
        DBObject rideDb = rides.findOne(query);
        ArrayList<String> requests = new ArrayList();
        try {
            requests = (ArrayList<String>) rideDb.get("requests");
        } catch (Exception e) {            
        }        
        if (requests == null) requests = new ArrayList();
        requests.add(userName);
        rideDb.put("requests", requests);
        rides.save(rideDb);
    }
    
    public ArrayList<String> getRideRequests(String rideId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(rideId));
        DBObject rideDb = rides.findOne(query);
        ArrayList<String> requests = new ArrayList();
        try {
            requests = (ArrayList<String>) rideDb.get("requests");
        } catch (Exception e) {}
        if (requests == null) requests = new ArrayList();
        return requests;
    }
    
    public void removeRequestFromRide(String userName, String rideId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(rideId));
        DBObject rideDb = rides.findOne(query);
        ArrayList<String> requests = new ArrayList();
        try {
            requests = (ArrayList<String>) rideDb.get("requests"); 
        } catch (Exception e) {}
        if (requests == null) requests = new ArrayList();
        requests.remove(userName);
        rideDb.put("requests", requests);
        rides.save(rideDb);
    }
    
    public void acceptRequest(String userName, String rideId) {        
        removeRequestFromRide(userName, rideId);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(rideId));
        DBObject rideDb = rides.findOne(query);
        ArrayList<String> coriders = new ArrayList();
        try {
            coriders = (ArrayList<String>) rideDb.get("coriders");
        } catch (Exception e) {}
        if (coriders == null) coriders = new ArrayList();
        coriders.add(userName);
        rideDb.put("coriders", coriders);
        rides.save(rideDb);
    }
    
    public void deleteRide(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        rides.findAndRemove(query);
    }
    
}
