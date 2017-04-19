package com.eis.czc.service.serviceImpl;

import com.eis.czc.model.Article;
import com.eis.czc.service.ArticleService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zcoaolas on 2017/4/16.
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String prefix= Parameter.PREFIX114;

    public JSONObject addArticle(Article article){
        JSONObject bodyJson = JSONObject.fromObject(article);
        bodyJson.remove("id");
        bodyJson.remove("ar_editor");
        System.out.println(bodyJson.toString());
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(bodyJson, httpHeaders);

        JSONObject jsonGot = restTemplate.postForEntity(prefix+"Article/", httpEntity, JSONObject.class).getBody();
        return jsonGot.isEmpty() ? null : jsonGot;
    }

    public JSONObject getAllArticles(){
        return restTemplate.getForObject(prefix+"Article/", JSONObject.class);
    }

    /*private JSONObject articleToJson(Article article){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ar_title", rmpBlog.getBlog_title());
        jsonObject.put("ar_place", rmpBlog.getBlog_place());
        jsonObject.put("ar_category", rmpBlog.getBlog_category());
        jsonObject.put("ar_content", rmpBlog.getBlog_content());

        JSONObject authorObj = new JSONObject();
        authorObj.put("id", rmpBlog.getAuthor());
        jsonObject.put("ar_author", authorObj);

        JSONObject editorObj = new JSONObject();
        editorObj.put("id", rmpBlog.getEditor());
        jsonObject.put("ar_editor", editorObj);

        JSONArray imgUrlList = new JSONArray();
        for (Long imgUrlId: rmpBlog.getImg_url_list()){
            JSONObject imgUrlJ = new JSONObject();
            imgUrlJ.put("id", imgUrlId);
            imgUrlList.add(imgUrlJ);
        }
        jsonObject.put("ar_url_list", imgUrlList);

        JSONArray timeList = new JSONArray();
        for (Long timeId: rmpBlog.getTime_list()){
            JSONObject timeJ = new JSONObject();
            timeJ.put("id", timeId);
            timeList.add(timeJ);
        }
        jsonObject.put("ar_time_list", timeList);

        // TODO Support all attributes

        System.out.println(jsonObject.toString());
        return jsonObject;
    }*/

}
