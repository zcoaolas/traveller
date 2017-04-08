package com.eis.czc.model;

import com.eis.czc.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcoaolas on 2017/4/6.
 */
@Component
public class UserPool {
    private Map<String, User> userMap;
    private static final String salt = "9f0sdu>jp-523";

    public UserPool () {
        userMap = new HashMap<String, User>();
    }

    /**
     *
     * @param u a User
     * @return the hash code of the User
     */
    public int addUser(User u){
        userMap.put(u.getU_name(), u);
        return (u.getU_name() + u.getU_id().toString() + salt).hashCode();
    }

    public User getUser(String uname){
        return userMap.get(uname);
    }

    public boolean validateUser(String uname, int hashCode){
        if(userMap.containsKey(uname)){
             Long uid = userMap.get(uname).getU_id();
             if (hashCode == (uname + uid + salt).hashCode())
                 return true;
        }
        return false;
    }
}
