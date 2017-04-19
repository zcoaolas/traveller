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
    private static UserPool userPool = null;

    private UserPool () {
        userMap = new HashMap<String, User>();
    }

    public static UserPool getInstance(){
        if (userPool == null)
            userPool = new UserPool();
        return userPool;
    }

    /**
     *
     * @param u a User
     * @return the hash code of the User
     */
    public int addUser(User u){
        userMap.put(u.getU_name(), u);
        return (u.getU_name() + u.getId().toString() + salt).hashCode();
    }

    public User getUser(String uname){
        return userMap.get(uname);
    }

    /**
     *
     * @param uname u_name in http header
     * @param hashCode u_hash in http header
     * @return an User if valid, null otherwise
     */
    public User validateUser(String uname, Integer hashCode){
        if(userMap.containsKey(uname)){
            User u = userMap.get(uname);
            Long uid = u.getId();
            if (hashCode == (uname + uid + salt).hashCode())
                return u;
        }
        return null;
    }

    public void userLogout(String u_name){
        userMap.remove(u_name);
    }
}
