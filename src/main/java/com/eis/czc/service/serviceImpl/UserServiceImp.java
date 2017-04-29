package com.eis.czc.service.serviceImpl;

import com.eis.czc.model.User;
import com.eis.czc.service.UserService;
import com.eis.czc.util.Parameter;
import com.eis.czc.model.UserPool;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2017/4/2 0002.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String prefix= Parameter.PREFIX114;

    public User getUserById(Long id){
        HttpEntity<Object> httpEntity=new HttpEntity<>(httpHeaders);

        JSONObject objRec = restTemplate.exchange(prefix+"User/" + id.toString(),
                HttpMethod.GET, httpEntity, JSONObject.class).getBody();
        if (objRec.isEmpty()){
            return null;
        }
        return (User)JSONObject.toBean(objRec, User.class);
    }

    public User getUserByNameAndPwd(String u_name, String u_pwd){
        HttpEntity<Object> httpEntity=new HttpEntity<Object>(httpHeaders);

        JSONObject objRec = restTemplate.exchange(
                prefix+"User/?User.u_name="+u_name+"&User.u_password="+u_pwd,
                HttpMethod.GET, httpEntity, JSONObject.class).getBody();
        if (objRec.isEmpty()){
            return null;
        }
        JSONArray jArr = objRec.getJSONArray("User");
        JSONObject tmp = ((JSONObject)jArr.get(0));
        return (User)JSONObject.toBean(tmp, User.class);
    }

    public Long addUser(User u){
        if (u_nameExists(u.getU_name())){
            return null;
        }

        HttpEntity<JSONObject> httpEntity=new HttpEntity<JSONObject>(JSONObject.fromObject(u), httpHeaders);

        JSONObject jsonGot = restTemplate.postForEntity(prefix+"User/", httpEntity, JSONObject.class).getBody();
        return jsonGot.isEmpty() ? null : (Long) jsonGot.get("id");
    }

    public JSONObject getUsers(){
        JSONObject jsonRes = restTemplate.getForObject(prefix+"User/", JSONObject.class);
        return jsonRes;
        /*JSONArray jsonUsers = jsonRes.getJSONArray("User");
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < jsonUsers.size(); i++){
            JSONObject jsonU = (JSONObject) jsonUsers.get(i);
            userList.add(jsonToUser(jsonU));
        }
        return userList;*/
    }

    public void updateUser(User u){

        HttpEntity<Object> httpEntity=new HttpEntity<Object>(JSONObject.fromObject(u), httpHeaders);
        restTemplate.exchange(prefix+"User/" + u.getId().toString(),
                HttpMethod.PUT, httpEntity, JSONObject.class);
    }

    /*private User jsonToUser(JSONObject jsonUser){
        return new User((Long) jsonUser.get("id"), jsonUser.getString("u_name"),
                jsonUser.getString("u_mail"), jsonUser.getString("u_password"), (Integer) jsonUser.get("u_role"));
    }*/

    /*private JSONObject userToJson(User u){
        JSONObject jsonObject = new JSONObject();
        if (u.getId() != null){
            jsonObject.put("id", u.getId());
        }
        jsonObject.put("u_name", u.getU_name());
        jsonObject.put("u_mail", u.getU_mail());
        jsonObject.put("u_password", u.getU_password());
        jsonObject.put("u_role", u.getU_role());
        return jsonObject;
    }*/

    private boolean u_nameExists(String u_name){
        HttpEntity<Object> httpEntity=new HttpEntity<Object>(httpHeaders);
        JSONObject objRec = restTemplate.exchange(
                prefix+"User/?User.u_name="+u_name,
                HttpMethod.GET, httpEntity, JSONObject.class).getBody();
        return !objRec.isEmpty();
    }

    public void deleteTYZ(){
        JSONObject jsonRes = restTemplate.getForObject(prefix+"User/", JSONObject.class);
        JSONArray users = jsonRes.getJSONArray("User");
        HttpEntity<Object> httpEntity=new HttpEntity<Object>(httpHeaders);

        for (Object anUser: users){
            JSONObject user = JSONObject.fromObject(anUser);
            String username = user.getString("u_name");
            if(username.indexOf("tianyizhang") == 0){
                restTemplate.exchange(prefix+"User/" + user.getLong("id"),
                        HttpMethod.DELETE, httpEntity, JSONObject.class);
            }
        }
    }
}
