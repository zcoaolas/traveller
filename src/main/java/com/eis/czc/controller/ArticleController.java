package com.eis.czc.controller;

import com.eis.czc.model.*;
import com.eis.czc.service.*;
import com.eis.czc.util.SystemRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zcoaolas on 2017/4/16.
 */
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private UrlService urlService;

    private UserPool userPool = UserPool.getInstance();

    @RequestMapping(value = "/Article", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addArticle(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name,
                                              @RequestBody Article article) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null) {
            return new ResponseEntity<>(ret, HttpStatus.UNAUTHORIZED);
        }
        HttpHeaders headers = new HttpHeaders();

        article.setAr_author(u);

        List<Article_url> urlList = article.getAr_url_list();
        for (Article_url url: urlList){
            url.setId(urlService.addPicUrl(url));
        }
        article.setAr_url_list(urlList);

        List<Article_time> timeList = new LinkedList<>();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Article_time at = new Article_time(0L, ts.toString());
        at.setId(timeService.addTime(at));
        timeList.add(at);
        article.setAr_time_list(timeList);

        JSONObject jsonGot = articleService.addArticle(article);

        return new ResponseEntity<>(jsonGot, UserController.addHeaderAttributes(headers), HttpStatus.OK);
    }

    @RequestMapping(value = "/Article", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getArticle(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null) return new ResponseEntity<>(ret, HttpStatus.UNAUTHORIZED);
        HttpHeaders headers = new HttpHeaders();

        JSONObject allArticles = articleService.getAllArticles();
        JSONArray articleArray = allArticles.getJSONArray("Article");
        JSONArray filteredArticles = new JSONArray();

        // Filter article according to user role
        if (u.hasRole(SystemRole.ADMIN)){
            filteredArticles = articleArray;
        }
        else if (u.hasRole(SystemRole.EDITOR)){
            for (Object obj : articleArray){
                JSONObject anArticle = (JSONObject) obj;
                if (shouldReview(anArticle, u.getU_name()) ||
                        isAuthor(anArticle, u.getU_name()) ||
                        isEditor(anArticle, u.getU_name())){
                    filteredArticles.add(anArticle);
                }
            }
        }
        else if (u.hasRole(SystemRole.REVIEWER)){
            for (Object obj : articleArray){
                JSONObject anArticle = (JSONObject) obj;
                if (shouldReview(anArticle, u.getU_name()) ||
                        isAuthor(anArticle, u.getU_name())){
                    filteredArticles.add(anArticle);
                }
            }
        }
        else{
            for (Object obj : articleArray){
                JSONObject anArticle = (JSONObject) obj;
                if (isAuthor(anArticle, u.getU_name())){
                    filteredArticles.add(anArticle);
                }
            }
        }
        ret.put("Article", filteredArticles);
        return new ResponseEntity<>(ret, UserController.addHeaderAttributes(headers), HttpStatus.OK);
    }




    private boolean shouldReview(JSONObject anArticle, String username){
        JSONArray ar_reviewer = anArticle.getJSONArray("ar_reviewer");
        JSONArray ar_review_list = anArticle.getJSONArray("ar_review_list");
        int reviewer_size = ar_reviewer.size();
        int review_size = ar_review_list.size();
        if (review_size < reviewer_size){
            JSONObject targetReviewer = JSONObject.fromObject(ar_reviewer.get(review_size));
            if (username.equals(targetReviewer.getString("u_name"))) return true;
        }
        return false;
    }

    private boolean isAuthor(JSONObject anArticle, String username){
        JSONObject targetAuthor = JSONObject.fromObject(anArticle.get("ar_author"));
        return username.equals(targetAuthor.getString("u_name"));
    }

    private boolean isEditor(JSONObject anArticle, String username){
        JSONObject targetEditor = JSONObject.fromObject(anArticle.get("ar_editor"));
        return username.equals(targetEditor.getString("u_name"));
    }
}
