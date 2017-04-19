package com.eis.czc.service;

import com.eis.czc.model.Article_url;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zcoaolas on 2017/4/18.
 */
public class UrlServiceTest extends base.BaseTest {
    @Autowired
    UrlService urlService;

    @Test
    public void addUrlTest() throws Exception{
        Article_url url = new Article_url(0L, "https://static.pexels.com/photos/1029/landscape-mountains-nature-clouds.jpg");
        Long urlId = urlService.addPicUrl(url);
        if (urlId == null){
            throw new Exception("Add Article_url Failed.");
        }
        System.out.println(urlId);
    }
}
