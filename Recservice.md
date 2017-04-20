# Recservice

 RecUserService
---
> When register new user

```
public boolean addUser (User u);
```

> When user change his profile (age, job or gender)
```
public boolean updateProfile (User u);
```

 RecArticleService
---
> When post new article

```
public boolean addArticle(Article a);
```

> When article's profile was changed (title, place, author or category)
```
public boolean updateProfile(Article a);
```
 RecActionService
---
>When user view a blog(sessionId maybe u.u_hash)
```
public boolean view(User u, Article a, String sessionId);
```
>When user star a blog(sessionId maybe u.u_hash)
```
public boolean star(User u, Article a, String sessionId);
```
>When user click a blog from current blog. rectype
could be "RECS_FOR_USER"(recommendation items) and "RANKINGS" (most viewed or star items).
```
public boolean track(User u, Article from, Article to, String sessionId, String rectype);
```
 RecOmmendService
---
>To get general recommendations ar_ids for specific user viewing specific articles.
```
public List<String> recommandations(String uid, String ai, int numberOfResults, int offset);
```
>To get view together relation ar_ids for specific user viewing specific articles.
```
public List<String> otherUsersView(String uid, String aid, int numberOfResults, int offset);
```
>To get star together relation ar_ids for specific user viewing specific articles.
```
public List<String> otherUsersStar(String uid, String aid, int numberOfResults, int offset);
```
>To get profile similar ar_ids for specific user viewing specific articles.
```
 public List<String> relatedItems(String uid, String aid, int numberOfResults, int offset);
```
>To get most view ar_ids.
```
public List<String> mostViewedItems(int numberOfResults, int offset);
```
>To get most star ar_ids.
```
public List<String> mostStaredItems(int numberOfResults, int offset);
```


