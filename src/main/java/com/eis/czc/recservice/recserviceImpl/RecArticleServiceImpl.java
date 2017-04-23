package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.Article;
import com.eis.czc.recservice.RecArticleService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecArticleServiceImpl implements RecArticleService {
    @Autowired
    private RestTemplate restTemplate;

    public boolean addArticle (Article a){
        JSONObject object = new JSONObject();
        object.put("itemid",a.getId());
        object.put("itemtype","ITEM");
        object.put("profile", getArticleProfile(a));
        object = Parameter.authBody(object);
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(object);
        JSONObject result = restTemplate.postForEntity(Parameter.PREFIXREC+"profile/store", httpEntity, JSONObject.class).getBody();
        System.out.println(result);
        return true;
    }

    public boolean updateProfile (Article a){
        addArticle(a);
        return true;
    }
    private static String getArticleProfile (Article u){
        return String.format("{\\\"upa\\\":" +
                        "{\\\"ar_title\\\":\\\"%s\\\", \\\"ar_place\\\":\\\"%s\\\", \\\"ar_category\\\":\\\"%s\\\"," +
                "\\\"author\\\":\\\"%s\\\"}}",
                u.getAr_title()==null?"":u.getAr_title(),u.getAr_place()==null?"":u.getAr_place()
                ,u.getAr_category()==null?"":u.getAr_category(),u.getAr_author()==null?"":u.getAr_author().getU_name());
    }
}
