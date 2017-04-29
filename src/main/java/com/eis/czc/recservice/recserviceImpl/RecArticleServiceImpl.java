package com.eis.czc.recservice.recserviceImpl;

import com.eis.czc.model.Article;
import com.eis.czc.recservice.RecArticleService;
import com.eis.czc.util.Parameter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by john on 2017/4/20 0020.
 */
@Service
public class RecArticleServiceImpl implements RecArticleService {
    @Autowired
    private RestTemplate restTemplate;

    public boolean addArticle (Article a){
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("?apikey="+ Parameter.APIKEY);
            sb.append("&tenantid="+Parameter.TENANTID);
            sb.append("&itemid="+a.getId().toString());
            sb.append("&itemtype="+"ITEM");
            sb.append("&profile="+ getArticleProfile(a));
            String url = Parameter.PREFIXREC1+"profile/store"+sb.toString();
            String result = restTemplate.getForEntity(url, String.class).getBody();
            System.out.println(result);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return true;
        }
    }
    public boolean updateProfile (Article a){
        addArticle(a);
        return true;
    }
    private static String getArticleProfile (Article u){
        return String.format(
                        "ar_title:%s, ar_place:%s, ar_category:%s, author:%s",
                u.getAr_title()==null?"":u.getAr_title(),u.getAr_place()==null?"":u.getAr_place()
                ,u.getAr_category()==null?"":u.getAr_category(),u.getAr_author()==null?"":u.getAr_author().getU_name());
    }
}
