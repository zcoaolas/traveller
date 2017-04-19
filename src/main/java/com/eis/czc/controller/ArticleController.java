package com.eis.czc.controller;

import com.eis.czc.model.*;
import com.eis.czc.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*@RequestMapping(value = "/Blog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addBlog(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name,
                                              @RequestBody Article article){
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null){
            return new ResponseEntity<>(ret, HttpStatus.UNAUTHORIZED);
        }
        //RMPBlog rmpBlog = new RMPBlog();

        rmpBlog.setAuthor(u.getU_id());

        List<Long> urlList = new LinkedList<>();
        for (Article_url url: article.getImg_url_list()){
            urlList.add(urlService.addPicUrl(url));
        }
        rmpBlog.setImg_url_list(urlList);

        rmpBlog.setBlog_content(article.getBlog_content());

        rmpBlog.setBlog_title(article.getBlog_title());

        rmpBlog.setBlog_place(article.getBlog_place());

        rmpBlog.setBlog_category(article.getBlog_category());

        rmpBlog.setEditor(1492495616439L);

        List<Long> timeList = new LinkedList<>();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        timeList.add(timeService.addTime(new Article_time(ts.toString())));
        rmpBlog.setTime_list(timeList);

        JSONObject jsonGot = articleService.addBlog(rmpBlog);

        return new ResponseEntity<>(jsonGot, HttpStatus.OK);
    }*/

    @RequestMapping(value = "/Article", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addBlog(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name,
                                              @RequestBody Article article) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null) {
            return new ResponseEntity<>(ret, HttpStatus.UNAUTHORIZED);
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

        return new ResponseEntity<>(jsonGot, HttpStatus.OK);
    }

    @RequestMapping(value = "/Article", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getBlog(@RequestHeader("User-Hash") Integer u_hash, @RequestHeader("Username") String u_name) {
        User u = userPool.validateUser(u_name, u_hash);
        JSONObject ret = new JSONObject();
        if (u == null) {
            return new ResponseEntity<>(ret, HttpStatus.UNAUTHORIZED);
        }

        JSONObject allBlogs = articleService.getAllArticles();
        // TODO: Filter blog according to user role
        JSONArray blogs = allBlogs.getJSONArray("");
        return null;
    }


}
