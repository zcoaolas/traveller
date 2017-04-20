package com.eis.czc.recservice;

import com.eis.czc.model.Article;
import com.eis.czc.model.User;

/**
 * Created by john on 2017/4/20 0020.
 */
public interface RecActionService {
    public boolean view(User u, Article a, String sessionId);
    public boolean star(User u, Article a, String sessionId);
    public boolean track(User u, Article from, Article to, String sessionId, String rectype);
}
