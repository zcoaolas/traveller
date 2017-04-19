package com.eis.czc.controller;


import com.eis.czc.model.User;
import com.eis.czc.model.UserPool;
import com.eis.czc.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


/**
 * Created by zcoaolas on 2017/4/4.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private UserPool userPool = UserPool.getInstance();


    @RequestMapping(value = "/User/Login", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> authenticateUser(@RequestParam String u_name, @RequestParam String u_password) {
        System.out.println("Authenticating User " + u_name);
        //Map<String, Object> ret = new HashMap<>();
        JSONObject ret = new JSONObject();
        User user = userService.getUserByNameAndPwd(u_name, u_password);
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (user != null){
            ret = JSONObject.fromObject(user);
            int userHash = userPool.addUser(user);
            ret.put("u_hash", userHash);
            ret.put("u_password", "");
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
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);

        user.setU_role(1);
        Long u_id = userService.addUser(user);
        if (u_id == null){
            ret.put("message", "Registration Failed");
            return new ResponseEntity<>(ret, headers, HttpStatus.OK);
        }
        return authenticateUser(user.getU_name(), user.getU_password());
    }

    @RequestMapping(value = "/User/Logout", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> userLogout(@RequestHeader("User-Hash") Integer u_hash,
                                                 @RequestHeader("Username") String u_name){
        System.out.println("User Try to Logout " + u_name);
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (u == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        userPool.userLogout(u_name);
        ret.put("message", "Logged out successfully");
        System.out.println(u_name + " logged out.");
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/User", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> modifyUser(@RequestHeader("User-Hash") Integer u_hash,
                                                  @RequestHeader("Username") String u_name, @RequestBody User user){
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (u == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        Integer user_role = u.getU_role();
        Integer modified_role = user.getU_role();
        if (user_role <  modified_role){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        user.setU_password(userService.getUserById(user.getId()).getU_password());
        userService.updateUser(user);
        userPool.userLogout(user.getU_name());

        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/")
    public String indexPage() {
       return "index";
    }


    public static HttpHeaders addHeaderAttributes (HttpHeaders headers){
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, passwd");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Content-Type","application/json;charset=UTF-8");
        return headers;
    }
}
