## Traveller API

/User/Login GET 
---
> Authenticate the user.

*request*
```
?u_name=junmingcao&u_password=jmcpwd
```

*success response*
```
{
  "id": 1492599354338,
  "u_age": 20,
  "u_gender": 1,
  "u_job": "一般职业",
  "u_mail": "mail@qq.com",
  "u_name": "junmingcao",
  "u_password": "",
  "u_role": 1,
  "u_hash": 1170952320
}
``` 
  
*failed response* 
``` 
{
  "message": "Log in Failed"
}
```

/User/Reg POST
---
> Register a user.  
If registration success, system performs log in automatically.  
u_id and u_role is generated in back-end, front-end can fill them with 0.

*request*
```
{
  "u_age": 20,
  "u_gender": 1,
  "u_job": "一般职业",
  "u_mail": "mail@qq.com",
  "u_name": "junmingcao",
  "u_password": "sdfsfsw3",
  "u_role": 8
}
```

*success response*
```
{
  "id": 1492599354338,
  "u_age": 20,
  "u_gender": 1,
  "u_job": "一般职业",
  "u_mail": "mail@qq.com",
  "u_name": "junmingcao",
  "u_password": "",
  "u_role": 1,
  "u_hash": 1170952320
}
```

*failed response*
```
{
  "message": "Registration Failed"
}
```

/User/Logout POST
---
> !! All the following operations need the following two headers indicating who you are.

*request header*
```
User-Hash -> -893712911
Username -> test_uname
```

*request body*
```
{}
```

*success response*
```
{
  "message": "Logged out successfully"
}
```

*failed response*
```
{
  "message": "Please Log in First"
}
```

/Article POST
---
> Write and submit a new article.

*request body*
```
{
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。塞伦盖蒂国家公园有14,750平方公里，几乎相当于整个北京市城区的面积总和，但越野车只能在固定的土路上奔跑，不许随意横穿。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "坦桑尼亚",
  "ar_category": "自然风光",
  "ar_author": {},
  "ar_reviewer": [],
  "ar_editor": {},
  "ar_time_list": [],
  "ar_url_list": [
    {
      "ar_url": "https://img3.doubanio.com/view/note/large/public/p38270091.jpg"
    },
    {
      "ar_url": "https://img1.doubanio.com/view/note/large/public/p38269988.jpg"
    }
  ],
  "ar_tag_list": [],
  "ar_review_list": []
}
```

*success response*
```
{
  "type": "article",
  "id": 1492605062619,
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。塞伦盖蒂国家公园有14,750平方公里，几乎相当于整个北京市城区的面积总和，但越野车只能在固定的土路上奔跑，不许随意横穿。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "坦桑尼亚",
  "ar_category": "自然风光",
  "ar_like_list": "",
  "ar_collect_list": "",
  "ar_read_list": "",
  "ar_author": {
    "id": 1492492007079,
    "u_name": "chenzhan",
    "u_mail": "cz@cz.com",
    "u_password": "",
    "u_role": 4
  },
  "ar_reviewer": [],
  "ar_time_list": [
    {
      "id": 1492605058905,
      "ar_time": "2017-04-19 20:31:00.004"
    }
  ],
  "ar_url_list": [
    {
      "id": 1492605058672,
      "ar_url": "https://img3.doubanio.com/view/note/large/public/p38270091.jpg"
    },
    {
      "id": 1492605058793,
      "ar_url": "https://img1.doubanio.com/view/note/large/public/p38269988.jpg"
    }
  ],
  "ar_tag_list": [],
  "ar_review_list": []
}
```

*failed response*
```
401 Unauthorized
{}
```

/Article GET
---
> GET all articles that you are able to read  

>ADMIN: all articles  
EDITOR: articles that you should edit or review  
REVIEWER: articles that you should review  
EVERYONE: articles you wrote  

*success response*
```
{
  "Article": [
    {
      "id": 1492605062619,
      "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。",
      "ar_title": "与一万只角马迎面相逢(二）",
      "ar_category": "自然风光",
      "ar_author": {
        "id": 1492492007079,
        "u_name": "chenzhan",
        "u_mail": "cz@cz.com",
        "u_password": "",
        "u_role": 4
      },
      "ar_reviewer": [
        {
          "id": 1492443754057,
          "u_name": "junmingcao",
          "u_mail": "mail@qq.com",
          "u_password": "",
          "u_role": 8
        }
      ],
      "ar_editor": {
        "id": 1492495616439,
        "u_name": "jiayangchen",
        "u_mail": "jyc@jyc.com",
        "u_password": "",
        "u_role": 7
      },
      "ar_time_list": [
        {
          "id": 1492605058905,
          "ar_time": "2017-04-19 20:31:00.004"
        }
      ],
      "ar_url_list": [
        {
          "id": 1492605058672,
          "ar_url": "https://img3.doubanio.com/view/note/large/public/p38270091.jpg"
        },
        {
          "id": 1492605058793,
          "ar_url": "https://img1.doubanio.com/view/note/large/public/p38269988.jpg"
        }
      ],
      "ar_tag_list": [],
      "ar_review_list": []
    }
  ]
}
```
