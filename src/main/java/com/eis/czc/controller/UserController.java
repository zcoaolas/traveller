package com.eis.czc.controller;


import com.eis.czc.model.Article;
import com.eis.czc.model.User;
import com.eis.czc.model.UserPool;
import com.eis.czc.recservice.RecActionService;
import com.eis.czc.recservice.RecOmmendService;
import com.eis.czc.recservice.RecUserService;
import com.eis.czc.service.ArticleService;
import com.eis.czc.service.UserService;
import com.eis.czc.util.SystemRole;
import net.sf.json.JSONArray;
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
    @Autowired
    private RecUserService recUserService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RecActionService recActionService;

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
        user.setId(u_id);

        // Recommendation
        recUserService.addUser(user);

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
        User fromUser = userService.getUserById(user.getId());

        Integer user_role = u.getU_role();
        Integer modified_role = user.getU_role();
        if (user_role <  modified_role){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        user.setU_password(userService.getUserById(user.getId()).getU_password());
        userService.updateUser(user);
        userPool.userLogout(user.getU_name());

        // Recommendation
        User toUser = userService.getUserById(user.getId());
        if (keyProfileChanged(fromUser, toUser)) recUserService.updateProfile(toUser);

        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/User", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getUser(@RequestHeader("User-Hash") Integer u_hash,
                                                 @RequestHeader("Username") String u_name){
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (u == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        JSONArray userRet = new JSONArray();
        JSONObject o = userService.getUsers();
        JSONArray users = (JSONArray) o.get("User");
        for (int i = 0; i < users.size(); i++){
            JSONObject usr = JSONObject.fromObject(users.get(i));
            if (u.hasRole((int) usr.get("u_role"))){
                userRet.add(usr);
            }
        }
        ret.put("User", userRet);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/User/Track", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> trackUser(@RequestHeader("User-Hash") Integer u_hash,
                                                @RequestHeader("Username") String u_name,
                                                @RequestParam String from_ar, @RequestParam String to_ar,
                                                @RequestParam String type) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (u == null) return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);

        Article fromArticle = (Article) JSONObject.toBean(articleService.getArticleById(Long.parseLong(from_ar)), Article.class);
        Article toArticle = (Article) JSONObject.toBean(articleService.getArticleById(Long.parseLong(to_ar)), Article.class);
        if(fromArticle.getId() == null || toArticle.getId() == null || (!type.equals("RECS_FOR_USER") && !type.equals("RANKING"))) {
            ret.put("message", "Parameter Error");
            return new ResponseEntity<>(ret, headers, HttpStatus.OK);
        }
        recActionService.track(u, fromArticle, toArticle, u_hash.toString(), type);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = {"/User", "/User/Logout", "/User/Reg"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<JSONObject> supportOptions() {
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(ret, addHeaderAttributes(headers), HttpStatus.OK);
    }

    @RequestMapping(value = "/")
    public String indexPage() {
        return "index";
    }


    public static HttpHeaders addHeaderAttributes (HttpHeaders headers){
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, passwd, User-Hash, Username");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Content-Type","application/json;charset=UTF-8");
        return headers;
    }

    private boolean keyProfileChanged(User fromUser, User toUser){
        if(toUser.getU_age() == null) return fromUser.getU_age() != null;
        if(toUser.getU_job() == null) return fromUser.getU_job() != null;
        if(toUser.getU_gender() == null) return fromUser.getU_gender() != null;

        return !(toUser.getU_age().equals(fromUser.getU_age()) &&
                toUser.getU_job().equals(fromUser.getU_job()) &&
                toUser.getU_gender().equals(fromUser.getU_gender()));
    }
}
