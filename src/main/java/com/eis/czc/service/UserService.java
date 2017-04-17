package com.eis.czc.service;


import com.eis.czc.model.User;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by john on 2017/4/2 0002.
 */
public interface UserService {
    User getUserById(Long id);

    User getUserByNameAndPwd(String u_name, String u_pwd);

    JSONObject getUsers();

    Long addUser(User u);

    void updateUser(User u);

}
