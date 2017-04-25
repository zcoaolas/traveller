package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.User;
import com.eis.czc.recservice.RecUserService;
import com.eis.czc.util.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecUserServiceImpl implements RecUserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    public boolean addUser (User u){
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("?apikey="+ Parameter.APIKEY);
            sb.append("&tenantid="+Parameter.TENANTID);
            sb.append("&itemid="+u.getId().toString());
            sb.append("&itemtype="+"USER");
            sb.append("&profile="+ getUserProfile(u));
            String url = Parameter.PREFIXREC1+"profile/store"+sb.toString();
        String result = restTemplate.getForEntity(url, String.class).getBody();
        System.out.println(result);}
        catch (Exception e)
        {
         e.printStackTrace();
        }
        finally {
            return true;
        }
    }
    public boolean updateProfile (User u){
        addUser(u);
        return true;
    }
    private static String getUserProfile (User u){
        return String.format("u_job:%s, u_age:%s, u_gender:%s",
                u.getU_job()==null?"":u.getU_job(), u.getU_gender()==null?"":u.getU_gender().toString(),
                u.getU_age()==null?"":u.getU_age().toString());
    }

}
