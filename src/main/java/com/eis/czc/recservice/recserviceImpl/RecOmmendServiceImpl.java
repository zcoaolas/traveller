package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.Article;
import com.eis.czc.model.User;
import com.eis.czc.recservice.RecOmmendService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecOmmendServiceImpl implements RecOmmendService {

    @Autowired
    private RestTemplate restTemplate;
    public List<String> otherUsersView(String uid, String aid, int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&itemid="+aid);
        sb.append("&userid="+uid);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"otherusersalsoviewed"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }
    public List<String> otherUsersStar(String uid, String aid, int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&itemid="+aid);
        sb.append("&userid="+uid);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"otherusersalsobought"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }

    public List<String> recommandations(String uid, String aid, int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&itemid="+aid);
        sb.append("&userid="+uid);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"recommendationsforuser"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }
    public List<String> relatedItems(String uid, String aid, int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&itemid="+aid);
        sb.append("&userid="+uid);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"relateditems"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }
    public List<String> mostViewedItems(int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"mostvieweditems"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }

    public List<String> mostStaredItems(int numberOfResults, int offset){
        StringBuilder sb = new StringBuilder();
        sb.append("?apikey="+ Parameter.APIKEY);
        sb.append("&tenantid="+Parameter.TENANTID);
        sb.append("&numberOfResults="+Integer.toString(numberOfResults));
        sb.append("&offset="+Integer.toString(offset));
        JSONObject result = restTemplate.exchange(Parameter.PREFIXREC+"mostboughtitems"+sb.toString(),
                HttpMethod.GET, null, JSONObject.class).getBody();
        System.out.println(result);
        List<String> ids = new LinkedList<String>();
        JSONObject[] recommendedItems = (JSONObject[])result.get("recommendedItems");
        for( JSONObject i : recommendedItems)
            ids.add(i.getString("id"));
        return ids;
    }
}
