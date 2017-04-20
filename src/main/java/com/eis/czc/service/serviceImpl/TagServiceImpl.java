package com.eis.czc.service.serviceImpl;

import com.eis.czc.model.Article_tag;
import com.eis.czc.service.TagService;
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
public class TagServiceImpl implements TagService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String prefix= Parameter.PREFIX114;

    public Long addTag(Article_tag articletag){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ar_tag", articletag.getAr_tag());
        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonObject, httpHeaders);
        JSONObject jsonGot = restTemplate.postForEntity(prefix+"Article_tag/", httpEntity, JSONObject.class).getBody();
        return jsonGot.isEmpty() ? null : (Long) jsonGot.get("id");
    }
}
