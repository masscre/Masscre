package go.logic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import go.model.User;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import go.setting.DatabaseSetting;
import java.util.ArrayList;
import java.util.Iterator;

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
    
}
