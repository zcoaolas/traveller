package com.eis.czc.recservice;

import com.eis.czc.model.User;

/**
 * Created by john on 2017/4/20 0020.
 */
public interface RecUserService {
    public boolean addUser (User u);
    public boolean updateProfile (User u);
}
