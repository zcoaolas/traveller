package com.eis.czc.controller;


import com.eis.czc.model.User;
import com.eis.czc.model.UserPool;
import com.eis.czc.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zcoaolas on 2017/4/4.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private static UserPool userPool = new UserPool();

    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.getUserById(id);
        if (user == null) {
            //System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }*/

    @RequestMapping(value = "/User/Login", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> authenticateUser(@RequestParam String u_name, @RequestParam String u_password) {
        System.out.println("Authenticating User " + u_name);
        JSONObject ret = new JSONObject();
        User user = userService.getUserByNameAndPwd(u_name, u_password);
        if (user != null){
            int userHash = userPool.addUser(user);
            ret.put("u_hash", userHash);
            ret.put("u_name", user.getU_name());
            ret.put("u_mail", user.getU_mail());
            ret.put("u_role", user.getU_role());
        }
        else{
            ret.put("message", "Log in Failed");
        }
        return new ResponseEntity<JSONObject>(ret, HttpStatus.OK);
    }

    @RequestMapping(value = "/User/Reg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> addUser(@RequestBody User user){
        System.out.println("Adding User " + user.getU_name());
        JSONObject ret = new JSONObject();
        user.setU_role(1);
        Long u_id = userService.addUser(user);
        if (u_id == null){
            ret.put("message", "Registration Failed");
            return new ResponseEntity<JSONObject>(ret, HttpStatus.OK);
        }
        return authenticateUser(user.getU_name(), user.getU_password());
    }

    @RequestMapping(value = "/")
    public String getUser() {
       return "index";
    }
}
