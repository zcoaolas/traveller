package com.eis.czc.service;

import com.eis.czc.model.Article;
import net.sf.json.JSONObject;

/**
 * Created by zcoaolas on 2017/4/16.
 */
public interface ArticleService {
    JSONObject addArticle(Article article);

    JSONObject getAllArticles();

    JSONObject updateArticle(Article article);
    JSONObject updateArticle(JSONObject article);

    JSONObject getArticleById(Long articleId);
}
