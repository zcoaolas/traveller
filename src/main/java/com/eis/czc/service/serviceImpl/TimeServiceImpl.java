package com.eis.czc.service.serviceImpl;

import com.eis.czc.model.Article_time;
import com.eis.czc.service.TimeService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zcoaolas on 2017/4/18.
 */
@Service
public class TimeServiceImpl implements TimeService{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String prefix= Parameter.PREFIX114;

    public Long addTime(Article_time articletime){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ar_time", articletime.getAr_time());
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, httpHeaders);
        JSONObject jsonGot = restTemplate.postForEntity(prefix+"Article_time/", httpEntity, JSONObject.class).getBody();
        return jsonGot.isEmpty() ? null : (Long) jsonGot.get("id");
    }
}
