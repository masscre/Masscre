package go.dto;

import com.mongodb.*;
import go.web.model.Ride;
import go.web.model.User;
import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.bson.types.ObjectId;
import go.utils.Text;
import go.web.model.Group;
import go.web.model.Message;

public class Database {

    private Mongo mongo;
    private DB db;
    private DBCollection users;
    private DBCollection rides;
    private DBCollection groups;
    public static Database database = new Database();

    Database() {
        try {
            this.mongo = new Mongo("masscre.cz");
            this.db = mongo.getDB("gotogether");
            this.users = db.getCollection("users");
            this.rides = db.getCollection("rides");
            this.groups = db.getCollection("groups");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DATABASE: No connection");
        } catch (MongoException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DATABASE: No connection");
        }
    }

    public void promoteUser(String id, int level) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject user = users.findOne(query);
        user.put("rights", level);
        users.save(user);
    }

    public String getUserName(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject user = users.findOne(query);
        return user.get("username").toString();
    }
    
    public String getGroupName(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject user = groups.findOne(query);
        return user.get("groupName").toString();
    }

    public void removeUser(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject user = users.findAndRemove(query);
        System.out.println("DATABASE: removing user " + user.get("username"));
    }
    
    public void removeGroup(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject group = groups.findAndRemove(query);
        System.out.println("DATABASE: removing group " + group.get("groupName"));
        String userId = (String) group.get("owner");
        BasicDBObject query2 = new BasicDBObject();
        query2.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query2);
        ArrayList<String> groupsList = new ArrayList();
        try {
            groupsList = (ArrayList<String>) user.get("groups");
        } catch (Exception e) {}
        if (groupsList == null) groupsList = new ArrayList();
        Iterator it = groupsList.iterator();
        ArrayList<String> newList = new ArrayList();
        while(it.hasNext()) {
            String i = (String) it.next();
            if (!i.equals(id)) {
                newList.add(i);
            }
        }
        user.put("groups", newList);
        users.save(user);
    }

    public ArrayList<User> getUsers() {
        System.out.println("Getting users");
        ArrayList<User> usersArray = new ArrayList<User>();
        List<DBObject> toArray = users.find().toArray();
        Iterator it = toArray.iterator();
        while (it.hasNext()) {
            User user = new User();
            DBObject dbObj = (DBObject) it.next();
            user.setFirstname(dbObj.get("firstname").toString());
            user.setLastname(dbObj.get("lastname").toString());
            user.setUsername(dbObj.get("username").toString());
            try {
                user.setRights(Integer.parseInt(dbObj.get("rights").toString()));
            } catch (Exception e) {
            }
            usersArray.add(user);
        }
        return usersArray;
    }
    
    public User getUser(String id) {
        try {
            User user = null;
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            DBObject findOne = users.findOne(query);
            String firstname = "";
            String lastname = "";
            String username = "";
            String email = "";
            try {
                firstname = findOne.get("firstname").toString();
            } catch (Exception e) {}
            try {
                lastname = findOne.get("lastname").toString();
            } catch (Exception e) {}
            try {
                username = findOne.get("username").toString();
            } catch (Exception e) {}
            try {
                email = findOne.get("email").toString();
            } catch (Exception e) {}
            user = new User(firstname, lastname, username, email, id);
            return user;
        } catch (Exception e) {}
        return null;
    }

    public void addRide(ObjectId userId, Ride ride) {
        BasicDBObject query = new BasicDBObject();
        query.put("day", ride.getDay());
        query.put("month", ride.getMonth());
        query.put("year", ride.getYear());
        query.put("hour", ride.getHour());
        query.put("minute", ride.getMinute());
        query.put("from", ride.getFrom());
        query.put("to", ride.getTo());
        query.put("userId", userId);
        rides.save(query);
        DBObject rideDb = rides.findOne(query);
        String rideId = rideDb.get("_id").toString();
        BasicDBObject query2 = new BasicDBObject();
        query2.put("_id", userId);
        DBObject userDb = users.findOne(query2);
        ArrayList ridesList = new ArrayList();
        if (userDb.get("rides") != null) {
            ridesList = (ArrayList) userDb.get("rides");
        }
        ridesList.add(rideId);
        userDb.put("rides", ridesList);
        users.save(userDb);
    }

    public ArrayList<Ride> getUserRides(ObjectId userId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", userId);
        DBObject user = users.findOne(query);
        ArrayList<String> ridesStringId = new ArrayList();
        try {
            ridesStringId = (ArrayList<String>) user.get("rides");
        } catch (Exception e) {
            return null;
        }
        
        if (ridesStringId == null) {
            return null;
        }
        
        Iterator it = ridesStringId.iterator();
        ArrayList<Ride> rideList = new ArrayList<Ride>();
        while (it.hasNext() == true) {
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
            r.setId(rideDb.get("_id").toString());
            rideList.add(r);
        }
        return rideList;
    }
    
    public ArrayList<Ride> getUpcomingRides(String userId) {
        ArrayList<Ride> upcomingRides = new ArrayList();
        ArrayList<User> friends = (ArrayList<User>) getUserFriendsList(userId);
        if (friends != null) {
            Iterator it = friends.iterator();        
            while(it.hasNext()) {
                User friend = getUser((String)it.next());            
                ArrayList<Ride> friendRides;
                friendRides = getUserRides(friend.getId());
                if (friendRides != null) {
                    for(int i = 0; i < friendRides.size(); i++) {
                    Ride r = friendRides.get(i);                    
                    r.setOwnerName(friend.getFirstname()+" "+friend.getLastname());
                    upcomingRides.add(r);
                    }
                }                
            }
        }
        return upcomingRides;
    }

    public User login(String username, String password) {
        System.out.println("### DATABASE: finding user " + username);
        //password = go.utils.Hash.hashToSha(password);
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        query.put("password", password);
        DBObject user = null;
        try {
            user = users.findOne(query);
        } catch (Exception e) {
            System.out.println("### ERROR: database problem");
        }
        if (user != null) {
            System.out.println("### DATABASE: user " + username + " found");
            int rights = 0;
            try {
                rights = Integer.parseInt(user.get("rights").toString());
            } catch (Exception e) {
            }
            User u = new User(user.get("username").toString(), rights, user.get("_id").toString(), user.get("firstname").toString(), user.get("lastname").toString());
            return u;
        } else {
            System.out.println("### DATABASE: user " + username + " not found");
            return null;
        }
    }

    public ObjectId getUserId(String username) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBObject user = users.findOne(query);
        return (ObjectId) user.get("_id");
    }

    public boolean userExist(String username) {
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBObject user = null;
        try {
            user = users.findOne(query);
        } catch (Exception e) {
            System.out.println("ERROR: database problem");
        }
        return user != null;
    }

    public void register(String firstname, String lastname, String username, String email, String password) {
        //String hashPassword = go.utils.Hash.hashToSha(password);        
        BasicDBObject query = new BasicDBObject();
        query.put("firstname", firstname);
        query.put("lastname", lastname);
        query.put("username", username);
        query.put("email", email);
        query.put("password", password);
        query.put("rights", 0);
        users.save(query);
        System.out.println("### DATABASE: user " + username + " registered ... password hash: " + password);
    }

    public ArrayList<User> getUsersByFirstName(String firstname) {
        firstname = Text.removeCapitalAndDiacritic(firstname);
        BasicDBObject query = new BasicDBObject();
        List<DBObject> toArray = users.find().toArray();
        ArrayList<User> usr = new ArrayList();
        Iterator it = toArray.iterator();
        while (it.hasNext()) {
            DBObject d = (DBObject) it.next();
            String removeCapitalAndDiacritic = Text.removeCapitalAndDiacritic(d.get("firstname").toString());
            if (removeCapitalAndDiacritic.equals(firstname)) {
                User u = null;
                String fName = d.get("firstname").toString();
                String lName = d.get("lastname").toString();
                String uName = d.get("username").toString();
                String email = null;
                try {
                    email = d.get("email").toString();
                } catch (Exception e) {
                }
                String id = d.get("_id").toString();
                try {
                    u = new User(fName, lName, uName, email, id);
                } catch (Exception e) {
                }
                usr.add(u);
            }
        }
        return usr;
    }

    public ArrayList<User> getUsersByLastName(String lastname) {
        lastname = Text.removeCapitalAndDiacritic(lastname);
        BasicDBObject query = new BasicDBObject();
        List<DBObject> toArray = users.find().toArray();
        ArrayList<User> usr = new ArrayList();
        Iterator it = toArray.iterator();
        while (it.hasNext()) {
            DBObject d = (DBObject) it.next();
            String removeCapitalAndDiacritic = Text.removeCapitalAndDiacritic(d.get("lastname").toString());
            if (removeCapitalAndDiacritic.equals(lastname)) {
                User u = null;
                String fName = d.get("firstname").toString();
                String lName = d.get("lastname").toString();
                String uName = d.get("username").toString();
                String email = null;
                try {
                    email = d.get("email").toString();
                } catch (Exception e) {
                }
                String id = d.get("_id").toString();
                try {
                    u = new User(fName, lName, uName, email, id);
                } catch (Exception e) {
                }
                usr.add(u);
            }
        }
        return usr;
    }

    public ArrayList<User> getUsersByEmail(String email) {
        email = Text.removeCapitalAndDiacritic(email);
        BasicDBObject query = new BasicDBObject();
        List<DBObject> toArray = users.find().toArray();
        ArrayList<User> usr = new ArrayList();
        Iterator it = toArray.iterator();
        while (it.hasNext()) {
            DBObject d = (DBObject) it.next();
            String removeCapitalAndDiacritic = "";
            try {
                removeCapitalAndDiacritic = Text.removeCapitalAndDiacritic(d.get("email").toString());
            } catch (Exception e) {
            }
            if (removeCapitalAndDiacritic.equals(email)) {
                User u = null;
                String fName = d.get("firstname").toString();
                String lName = d.get("lastname").toString();
                String uName = d.get("username").toString();
                String uemail = null;
                try {
                    uemail = d.get("email").toString();
                } catch (Exception e) {
                }
                String id = d.get("_id").toString();
                try {
                    u = new User(fName, lName, uName, uemail, id);
                } catch (Exception e) {
                }
                usr.add(u);
            }
        }
        return usr;
    }
    
    public void addFriendRequestToUser(String userId, String friendId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(friendId));
        DBObject user = users.findOne(query);
        List friendsRequests = null;
        try {
            friendsRequests = (List) user.get("friendsRequests");
        } catch (Exception e) {}
        if (friendsRequests == null) {
            friendsRequests = new ArrayList();
        }
        friendsRequests.add(userId);
        user.put("friendsRequests", friendsRequests);
        users.save(user);
    }
    
    public List getUserFriendsRequests(String userId) {        
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        List list = new ArrayList();
        try {
            list = (List) user.get("friendsRequests");
        } catch (Exception e) {            
        }      
        try {
            list.isEmpty();
        } catch (Exception e){
            return new ArrayList();
        }
        return list;
    }
    
    public List<User> getUserFriendsList(String userId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        List list = new ArrayList();
        try {
            list = (List) user.get("friendsList");            
        } catch (Exception e) {
            return new ArrayList();
        }
        return list;
    }
    
    public void confirmFriend(String userId, String friendId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        List list = null;        
        try {
            list = (List) user.get("friendsRequests");
        } catch(Exception e) {}
        try {
            List list2 = new ArrayList();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                String id = (String) it.next();
                if (!friendId.equals(id)) {
                    list2.add(id);
                }
            }         
            user.put("friendsRequests", list2);
            users.save(user);
            addFriend(userId, friendId);
            addFriend(friendId, userId);
        } catch(Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void addFriend(String userId, String friendId) {
        if (isFriendOf(userId, friendId) == true) return;
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        List friendsList = null;
            try {
                friendsList = (List) user.get("friendsList");
            } catch (Exception e) {}
            try {
                friendsList.isEmpty();
            } catch (Exception e) {
                friendsList = new ArrayList();
            }
        friendsList.add(friendId);
        user.put("friendsList", friendsList);
        users.save(user);
    }
    
    public void rejectFriend(String userId, String friendId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        List list = null;
        try {
            list = (List) user.get("friendsRequests");
        } catch (Exception e) {}
        try {
            List list2 = new ArrayList();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                String id = (String) it.next();
                if (!friendId.equals(id)) {
                    list2.add(id);
                }
            }
            user.put("friendsRequests", list2);
        } catch (Exception e) {}
        users.save(user);
    }
    
    public boolean isFriendOf(String userId, String friendId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));        
        DBObject user = users.findOne(query);
        List list = new ArrayList();
        try {
            list = (ArrayList) user.get("friendsList");
        } catch (Exception e) {}
        if (list == null) return false;
        if (list.contains(friendId)) return true;
        return false;
    }
    
    public ArrayList<Group> getUserGroups(String userId) {
        ArrayList<Group> groupsList = new ArrayList();
        ArrayList<String> groupsId = new ArrayList();
        DBObject user = users.findOne(new ObjectId(userId));
        groupsId = (ArrayList<String>) user.get("groups");
        Iterator it = groupsId.iterator();
        while(it.hasNext()) {
            Group group = new Group();
            String groupId = (String) it.next();
            DBObject groupDb = groups.findOne(new ObjectId(groupId));
            String groupName = groupDb.get("groupName").toString();            
            group.setGroupName(groupName);
            group.setId(groupId);
            group.setMembers((ArrayList<String>)groupDb.get("members"));
            group.setOwner(userId);
            groupsList.add(group);
        }
        return groupsList;
    }
    
    public void addGroupToUser(String userId, Group group) {
        BasicDBObject query = new BasicDBObject();
        query.put("groupName", group.getGroupName());
        query.put("owner", userId);
        query.put("members", new ArrayList<String>());
        groups.save(query);
        DBObject groupDb = groups.findOne(query);
        String groupId = groupDb.get("_id").toString();
        BasicDBObject query2 = new BasicDBObject();
        query2.put("_id", new ObjectId(userId));
        DBObject userDb = users.findOne(query2);
        ArrayList groupsList;
        try {
            groupsList = (ArrayList) userDb.get("groups");
        } catch (Exception e) {
            groupsList = new ArrayList<Group>();
        }
        if (groupsList == null) {
            groupsList = new ArrayList<Group>();
        }
        groupsList.add(groupId);
        userDb.put("groups", groupsList);
        users.save(userDb);
        
    }
    
    public void sendMessage(Message message) throws IOException {
        String receiverId = message.getTo();
        DBObject receiver = users.findOne(new ObjectId(receiverId));
        ArrayList messages;
        try {
            messages = (ArrayList) receiver.get("messages");
        } catch (Exception e) {
            messages = new ArrayList();
        }
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(message.Serialize());
        receiver.put("messages", messages);
        users.save(receiver);
    }
    
    public ArrayList getUserMessages(String userId) throws IOException, ClassNotFoundException {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(userId));
        DBObject user = users.findOne(query);
        ArrayList messagesByte;
        try {
            messagesByte = (ArrayList) user.get("messages");
        } catch (Exception e) {
            messagesByte = new ArrayList();
        }   
        ArrayList messages = new ArrayList();
        Iterator it = messagesByte.iterator();
        while(it.hasNext()) {
            byte[] m = (byte[]) it.next();            
            Message message = Message.Deserialize(m);
            System.out.println(message.isReaded());
            messages.add(message);
        }
        return messages;        
    }
    
    public Message readMessage(String userId, String id) throws IOException, ClassNotFoundException {
        ArrayList messages = getUserMessages(userId);
        ArrayList messagesNew = new ArrayList();
        Iterator it = messages.iterator();
        Message r = new Message();
        while(it.hasNext()) {
            Message m = (Message) it.next();
            m.setReaded(true);
            if (m.getId().equals(id)) {
                m.setReaded(true);                
                r = m;                
            }
            messagesNew.add(m.Serialize());
        }
        BasicDBObject query = new BasicDBObject();
        DBObject user = users.findOne(query);        
        user.put("messages", messagesNew);        
        users.save(user);
        return r;        
    }
    
}
