package com.eis.czc.recservice;

import com.eis.czc.model.Article;
import com.eis.czc.model.User;

import java.util.List;

/**
 * Created by john on 2017/4/20 0020.
 */
public interface RecOmmendService {
    public List<String> otherUsersView(String uid, String aid, int numberOfResults, int offset);
    public List<String> otherUsersStar(String uid, String aid, int numberOfResults, int offset);
    public List<String> recommandations(String uid, String ai, int numberOfResults, int offset);
    public List<String> relatedItems(String uid, String aid, int numberOfResults, int offset);
    public List<String> mostViewedItems(int numberOfResults, int offset);
    public List<String> mostStaredItems(int numberOfResults, int offset);
}
