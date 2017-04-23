package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.Article;
import com.eis.czc.model.User;
import com.eis.czc.recservice.RecActionService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecActionServiceImpl implements RecActionService {
    @Autowired
    private RestTemplate restTemplate;

    public boolean view(User u, Article a, String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&sessionid="+sessionId);
        sb.append("&itemid="+a.getId());
        sb.append("&itemdescription="+"itemdescription");
        sb.append("&itemurl="+"itemurl");
        sb.append("&userid="+u.getId());
        sb.append("&actioninfo="+getActionInfo(u,a,"view"));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"view"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        return true;
    }
    public boolean star(User u, Article a, String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&sessionid="+sessionId);
        sb.append("&itemid="+a.getId());
        sb.append("&itemdescription="+"itemdescription");
        sb.append("&itemurl="+"itemurl");
        sb.append("&userid="+u.getId());
        sb.append("&actioninfo="+getActionInfo(u,a,"buy"));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"buy"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        return true;
    }

    public boolean track(User u, Article from, Article to, String sessionId, String rectype){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&sessionid="+sessionId);
        sb.append("&itemfromid="+from.getId());
        sb.append("&itemtoid="+to.getId());
        sb.append("&rectype"+rectype);
        sb.append("&userid="+u.getId());
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"track"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        return true;
}

    private static String getActionInfo(User u, Article a, String action){
        return String.format("{\"action\":\"%s\"}",action);
    }
}
