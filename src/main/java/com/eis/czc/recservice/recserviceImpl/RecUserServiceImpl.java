package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.User;
import com.eis.czc.recservice.RecUserService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecUserServiceImpl implements RecUserService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean addUser (User u){
        JSONObject object = new JSONObject();
        object.put("itemid",u.getId());
        object.put("itemtype","USER");
        object.put("profile", getUserProfile(u));
        object = Parameter.authBody(object);
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(object);
        JSONObject result = restTemplate.postForEntity(Parameter.PREFIXREC+"profile/store", httpEntity, JSONObject.class).getBody();
        System.out.println(result);
        return true;
    }

    public boolean updateProfile (User u){
        addUser(u);
        return true;
    }

    private static String getUserProfile (User u){
        return String.format("{\\\"upa\\\":" +
                "{\\\"u_job\\\":\\\"%s\\\", \\\"u_gender\\\":\\\"%s\\\", \\\"u_age\\\":\\\"%s\\\"}}",
                u.getU_job(),u.getU_gender().toString(),u.getU_age().toString());
    }
}
