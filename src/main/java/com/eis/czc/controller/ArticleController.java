package com.eis.czc.controller;

import com.eis.czc.model.*;
import com.eis.czc.recservice.RecActionService;
import com.eis.czc.recservice.RecArticleService;
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

import static com.eis.czc.controller.UserController.addHeaderAttributes;

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
    @Autowired
    private RecArticleService recArticleService;
    @Autowired
    private RecActionService recActionService;

    private UserPool userPool = UserPool.getInstance();

    @RequestMapping(value = "/Article", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addArticle(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name,
                                              @RequestBody Article article) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        if (u == null) {
            return new ResponseEntity<>(ret, addHeaderAttributes(headers), HttpStatus.UNAUTHORIZED);
        }

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

        // Recommendation
        Article a = (Article) JSONObject.toBean(jsonGot, Article.class);
        recArticleService.addArticle(a);

        return new ResponseEntity<>(jsonGot, addHeaderAttributes(headers), HttpStatus.OK);
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
                if (shouldEdit(anArticle, u.getU_name())){
                    filteredArticles.add(anArticle);
                }
            }
        }
        else if (u.hasRole(SystemRole.REVIEWER)){
            for (Object obj : articleArray){
                JSONObject anArticle = (JSONObject) obj;
                if (shouldReview(anArticle, u.getU_name())){
                    filteredArticles.add(anArticle);
                }
            }
        }
        else{
            for (Object obj : articleArray){
                JSONObject anArticle = (JSONObject) obj;
                if (isAuthor(anArticle, u.getU_name()) ||
                        isPublished(anArticle)){
                    filteredArticles.add(anArticle);
                }
            }
        }
        ret.put("Article", filteredArticles);
        return new ResponseEntity<>(ret, addHeaderAttributes(headers), HttpStatus.OK);
    }

    @RequestMapping(value = "/Article", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> putArticle(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name,
                                                 @RequestBody Article article) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (u == null) return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);

        Article fromArticle = (Article) JSONObject.toBean(articleService.getArticleById(article.getId()), Article.class);
        List<Article_review> reviewList = article.getAr_review_list();
        User editor = article.getAr_editor();
        List<User> reviewerList = article.getAr_reviewer();
        List<Article_time> timeList = article.getAr_time_list();

        // Add review
        int review_size = reviewList.size();
        if(review_size >= 1 && review_size <= 4 &&
                ((reviewerList.size() >= review_size && review_size <= 3) || (review_size == 4))){
            User targetReviewer;
            Article_review targetReview = reviewList.get(review_size-1);
            if (review_size == 4) {
                targetReviewer = editor;
            }
            else{
                targetReviewer = reviewerList.get(review_size-1);
            }
            if (u_name.equals(targetReviewer.getU_name()) && null == targetReview.getId()){
                targetReview.setId(reviewService.addReview(targetReview));

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Article_time at = new Article_time(0L, ts.toString());
                at.setId(timeService.addTime(at));
                timeList.add(at);
            }
        }

        article.setAr_time_list(timeList);
        article.setAr_review_list(reviewList);

        JSONObject jsonGot = articleService.updateArticle(article);
        article.setId(jsonGot.getLong("id"));

        // Recommendation
        Article toArticle = (Article) JSONObject.toBean(jsonGot, Article.class);
        if (keyProfileChanged(fromArticle, toArticle)){
            recArticleService.updateProfile(toArticle);
        }

        return new ResponseEntity<>(jsonGot, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/Article/{articleId}/{operation}", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> collectArticleInfo(@RequestHeader("User-Hash") Integer u_hash,
                                                         @RequestHeader("Username") String u_name,
                                                         @PathVariable Long articleId, @PathVariable String operation){
        User user = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers = addHeaderAttributes(headers);
        if (user == null) return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);

        JSONObject articleGot = articleService.getArticleById(articleId);
        if (!articleGot.isEmpty()){
            if ("Like".equals(operation)) {
                String likeList = articleGot.getString("ar_like_list");
                likeList += user.getId().toString() + ";";
                articleGot.put("ar_like_list", likeList);
                JSONObject a = articleService.updateArticle(articleGot);

                // Recommendation
                Article aa = (Article) JSONObject.toBean(a, Article.class);
                recActionService.star(user, aa, u_hash.toString());
            }
            else if ("Collect".equals(operation)) {
                String collectList = articleGot.getString("ar_collect_list");
                collectList += user.getId().toString() + ";";
                articleGot.put("ar_collect_list", collectList);
                articleService.updateArticle(articleGot);
            }
            else if ("Read".equals(operation)) {
                String readList = articleGot.getString("ar_read_list");
                readList += user.getId().toString() + ";";
                articleGot.put("ar_read_list", readList);
                JSONObject a = articleService.updateArticle(articleGot);

                // Recommendation
                Article aa = (Article) JSONObject.toBean(a, Article.class);
                recActionService.view(user, aa, u_hash.toString());
            }
        }

        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = {"/Article"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<JSONObject> supportOptions() {
        JSONObject ret = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(ret, addHeaderAttributes(headers), HttpStatus.OK);
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
        if(review_size==3 && reviewer_size==3){
            JSONObject editor = JSONObject.fromObject(anArticle.get("ar_editor"));
            if (username.equals(editor.getString("u_name"))) return true;
        }
        return false;
    }

    private boolean shouldEdit(JSONObject anArticle, String username){
        return anArticle.has("ar_editor") &&
                anArticle.getJSONObject("ar_editor").has("u_name") &&
                anArticle.getJSONObject("ar_editor").getString("u_name").equals(username);
    }

    private boolean isAuthor(JSONObject anArticle, String username){
        JSONObject targetAuthor = JSONObject.fromObject(anArticle.get("ar_author"));
        if(username.equals(targetAuthor.getString("u_name"))){
            JSONObject editor = JSONObject.fromObject(anArticle.get("ar_editor"));
            JSONArray ar_reviewer = anArticle.getJSONArray("ar_reviewer");
            JSONArray ar_review_list = anArticle.getJSONArray("ar_review_list");
            if(editor.isEmpty() && ar_reviewer.isEmpty()) return true;
            if(ar_review_list.size() == 4) return true;
        }
        return false;
    }

    /*private boolean isEditor(JSONObject anArticle, String username){
        JSONObject targetEditor = JSONObject.fromObject(anArticle.get("ar_editor"));
        return username.equals(targetEditor.getString("u_name"));
    }*/

    private boolean isPublished(JSONObject anArticle){
        JSONArray review_list = JSONArray.fromObject(anArticle.get("ar_review_list"));
        if(review_list.size() == 4){
            JSONObject editorReview = (JSONObject) review_list.get(3);
            if (1 == (int) editorReview.get("ar_result")) return true;
        }
        return false;
    }

    private boolean keyProfileChanged(Article fromA, Article toA){
        if (toA.getAr_title() == null) return fromA.getAr_title() != null;
        if (toA.getAr_place() == null) return fromA.getAr_place() != null;
        if (toA.getAr_author() == null) return fromA.getAr_author() != null;
        if (toA.getAr_category() == null) return fromA.getAr_category() != null;

        return !(toA.getAr_title().equals(fromA.getAr_title()) &&
        toA.getAr_place().equals(fromA.getAr_place()) &&
        toA.getAr_author().equals(fromA.getAr_author()) &&
        toA.getAr_category().equals(fromA.getAr_category()));
    }
}
