package com.eis.czc.controller;


import com.eis.czc.model.User;
import com.eis.czc.model.UserPool;
import com.eis.czc.service.UserService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcoaolas on 2017/4/4.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private UserPool userPool = UserPool.getInstance();

    /*@RequestMapping(value = "/User/Login", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
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
    }*/

    @RequestMapping(value = "/User/Login", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> authenticateUser(@RequestParam String u_name, @RequestParam String u_password) {
        System.out.println("Authenticating User " + u_name);
        //Map<String, Object> ret = new HashMap<>();
        JSONObject ret = new JSONObject();
        User user = userService.getUserByNameAndPwd(u_name, u_password);
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
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
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/User/Reg", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addUser(@RequestBody User user){
        System.out.println("Adding User " + user.getU_name());
        JSONObject ret = new JSONObject();
        user.setU_role(1);
        Long u_id = userService.addUser(user);
        if (u_id == null){
            ret.put("message", "Registration Failed");
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        //return authenticateUser(user.getU_name(), user.getU_password());
        return null;
    }

    @RequestMapping(value = "/User/Logout", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> userLogout(@RequestHeader("User-Hash") Integer u_hash,
                                                 @RequestHeader("Username") String u_name){
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null){
            ret.put("message", "Please Log in First");
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        userPool.userLogout(u_name);
        ret.put("message", "Logged out successfully");
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(value = "/User", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllUsers(@RequestHeader("User-Hash") Integer u_hash,
                                                  @RequestHeader("Username") String u_name){
        User u = userPool.validateUser(u_name, u_hash);
        //if (u == null || !u.hasRole(Syro))
        return null;
    }

    @RequestMapping(value = "/")
    public String getUser() {
       return "index";
    }


    private HttpHeaders addHeaderAttributes (HttpHeaders headers){
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, passwd");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }
}
