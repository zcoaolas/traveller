package com.eis.czc.service.serviceImpl;

import com.eis.czc.model.Article_review;
import com.eis.czc.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String prefix= Parameter.PREFIX114;


    public Long addReview(Article_review articlereview){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ar_comment", articlereview.getAr_comment());
        jsonObject.put("ar_result", articlereview.getAr_result());
        jsonObject.put("ar_confidence", articlereview.getAr_confidence());
        jsonObject.put("ar_point", articlereview.getAr_point());
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, httpHeaders);
        JSONObject jsonGot = restTemplate.postForEntity(prefix+"Article_review/", httpEntity, JSONObject.class).getBody();
        return jsonGot.isEmpty() ? null : (Long) jsonGot.get("id");
    }
}
