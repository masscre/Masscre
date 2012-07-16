package go.utils;

import go.model.User;
import java.util.ArrayList;
import java.util.Iterator;

public class UserUtils {
    
    public static ArrayList<User> formatUsers(ArrayList<User> users) {
        Iterator it = users.iterator();
        ArrayList<User> formatedUsers = new ArrayList();
        while(it.hasNext()) {
            User u = (User) it.next();
            u.setFirstName(StringUtils.makeFirstCharUpperCase(u.getFirstName()));
            u.setLastName(StringUtils.makeFirstCharUpperCase(u.getLastName()));
            formatedUsers.add(u);
        }
        return formatedUsers;
    }
    
}
