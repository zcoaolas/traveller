package com.eis.czc.service;

import com.eis.czc.model.User;
import com.eis.czc.util.SystemRole;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by john on 2017/4/2 0002.
 */
public class UserServiceTest extends base.BaseTest {
    @Autowired
    UserService userService;

    @Test
    public void addUserTest(){
        User u= new User();
        u.setU_name("tomcat");
        u.setU_mail("t@g.com");
        u.setU_password("glassfish");
        u.setU_role(SystemRole.USER.getCharacter());
        Long uid = userService.addUser(u);
        System.out.println(uid);
    }

    @Test
    public void getUserTest(){
        User u = userService.getUserById(1491185141325L);
        System.out.println(u);
    }

    @Test
    public void LoginTest(){
        User u = userService.getUserByNameAndPwd("tomcat", "glassfish");
        System.out.println(u);
    }

    @Test
    public void updateUserTest(){
        User u= new User(1491273809543L, "name7", "7@liu.com", "pwd7", 0);
        userService.updateUser(u);
    }

    @Test
    public void getAllUsersTest(){
        //List<User> userList= userService.getUsers();
        //System.out.println(userList);
    }
}
