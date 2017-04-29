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

/User PUT
---
> Change personal info of an account  
~ But you cannot change password by this API  

*request header*
```
User-Hash -> -893712911
Username -> test_uname
```

*request body*
```
{
  "id": 1492495616439,
  "u_age": 0,
  "u_gender": 0,
  "u_job": "",
  "u_mail": "jyc@jyc.cn",
  "u_name": "jiayangchen",
  "u_password": "",
  "u_role": 7,
  "u_hash": -1906674918
}
```

*success response*
```
{}
```

*failed response*
```
401 Unauthorized
{}
```

/User GET
---
> Admin Only. Get all users.

*success response*
```
{
  "User": [
    {
      "id": 1492495616439,
      "u_name": "jiayangchen",
      "u_mail": "jyc@jyc.cn",
      "u_password": "",
      "u_role": 7
    },
    {
      "id": 1492505426906,
      "u_name": "editor",
      "u_mail": "editor@qq.com",
      "u_password": "",
      "u_role": 4
    },
    {
      "id": 1492505453965,
      "u_name": "reviewer",
      "u_mail": "reviewer@qq.com",
      "u_password": "",
      "u_role": 2
    }
  ]
}
```

*failed response*
```
401 Unauthorized
{}
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

/Article PUT
---
> Modify an article status.

*request body (first reviewer adds his review)*
```
{
  "type": "article",
  "id": 1492605062619,
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "",
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
  "ar_reviewer": [
    {
      "id": 1492443754057,
      "u_name": "junmingcao",
      "u_mail": "mail@qq.com",
      "u_password": "",
      "u_role": 8
    },
  ],
  "ar_editor": {
    "id": 1492495616439,
    "u_name": "jiayangchen",
    "u_mail": "jyc@jyc.cn",
    "u_password": "",
    "u_role": 7
  },
  "ar_time_list": [
    {
      "id": 1492605058905,
      "ar_time": "2017-04-19 20:31:00.004"
    },
    {
      "id": 1492841952576,
      "ar_time": "2017-04-22 14:19:09.664"
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
  "ar_review_list": [
    {
      "ar_comment": "This is a great article!!",
      "ar_result": 1,
      "ar_confidence": 77,
      "ar_point": 97
    }
  ]
}
```
*success response (first reviewer added his review successfully)*
```
{
  "type": "article",
  "id": 1492605062619,
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "",
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
    "u_mail": "jyc@jyc.cn",
    "u_password": "",
    "u_role": 7
  },
  "ar_time_list": [
    {
      "id": 1492605058905,
      "ar_time": "2017-04-19 20:31:00.004"
    },
    {
      "id": 1492841952576,
      "ar_time": "2017-04-22 14:19:09.664"
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
  "ar_review_list": [
    {
      "id": 1492841928912,
      "ar_comment": "This is a great article!!",
      "ar_result": 1,
      "ar_confidence": 77,
      "ar_point": 97
    }
  ]
}
```

*request body (add user 1492495616439 as a reviewer)*
```
{
  "type": "article",
  "id": 1492605062619,
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "",
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
  "ar_reviewer": [
    {
      "id": 1492443754057,
      "u_name": "junmingcao",
      "u_mail": "mail@qq.com",
      "u_password": "",
      "u_role": 8
    },
    {
    	"id": 1492495616439
    }
  ],
  "ar_editor": {
    "id": 1492495616439,
    "u_name": "jiayangchen",
    "u_mail": "jyc@jyc.cn",
    "u_password": "",
    "u_role": 7
  },
  "ar_time_list": [
    {
      "id": 1492605058905,
      "ar_time": "2017-04-19 20:31:00.004"
    },
    {
      "id": 1492841952576,
      "ar_time": "2017-04-22 14:19:09.664"
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
  "ar_review_list": [
    {
      "id": 1492841928912,
      "ar_comment": "This is a great article!!",
      "ar_result": 1,
      "ar_confidence": 77,
      "ar_point": 97
    }
  ]
}
```

*success response (user 1492495616439 added as a reviewer successfully)*
```
{
  "type": "article",
  "id": 1492605062619,
  "ar_content": "当第一只角马出现时，我们立刻惊呼了起来。塞伦盖蒂国家公园有14,750平方公里，几乎相当于整个北京市城区的面积总和，但越野车只能在固定的土路上奔跑，不许随意横穿。也就是说，整个游览，我们都是路边有什么看什么。常常目力所及，一大片原野上只有两三条路交错着伸向远方。但即便是这么有限的行驶范围，野生动物还是源源不断地在路边出现。角马是塞伦盖蒂的明星，所谓的东非动物大迁徙，最著名的镜头就是成千上万头角马奔跑着过河，不时有几只被河里的鳄鱼一口撕倒。\n东非动物大迁徙实际是持续全年的，超过一百万的动物从坦桑尼亚的塞伦盖蒂走到肯尼亚的马赛马拉，又随着新鲜长出的清草一路啃回塞伦盖蒂。旺季时，塞伦盖蒂会有150万角马和约25万只斑马经过，那时河边会挤满了越野车，房价和车费也会急剧飙升。\n5月初是雨季末尾，塞伦盖蒂从人、到车、到角马，都稀疏得很，那只傻乎乎的角马站在路边，目睹着两个中国年轻人突然嗷地一声跳起来，噼里啪啦快门按个不停。",
  "ar_title": "与一万只角马迎面相逢(二）",
  "ar_place": "",
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
  "ar_reviewer": [
    {
      "id": 1492443754057,
      "u_name": "junmingcao",
      "u_mail": "mail@qq.com",
      "u_password": "",
      "u_role": 8
    },
    {
      "id": 1492495616439,
      "u_name": "jiayangchen",
      "u_mail": "jyc@jyc.cn",
      "u_password": "",
      "u_role": 7
    }
  ],
  "ar_editor": {
    "id": 1492495616439,
    "u_name": "jiayangchen",
    "u_mail": "jyc@jyc.cn",
    "u_password": "",
    "u_role": 7
  },
  "ar_time_list": [
    {
      "id": 1492605058905,
      "ar_time": "2017-04-19 20:31:00.004"
    },
    {
      "id": 1492841952576,
      "ar_time": "2017-04-22 14:19:09.664"
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
  "ar_review_list": [
    {
      "id": 1492841928912,
      "ar_comment": "This is a great article!!",
      "ar_result": 1,
      "ar_confidence": 77,
      "ar_point": 97
    }
  ]
}
```

/Article/7654321/Like POST
---
> User likes an article which id is 7654321

*request body*
```
{}
```

*success response*
```
{}
```

*failed response*
```
401 Unauthorized
{}
```

/Article/7654321/Collect POST
---
> User puts an article which id is 7654321 into collection

*request body*
```
{}
```

*success response*
```
{}
```

*failed response*
```
401 Unauthorized
{}
```

/Article/7654321/Read POST
---
> User reads an article which id is 7654321

*request body*
```
{}
```

*success response*
```
{}
```

*failed response*
```
401 Unauthorized
{}
```

/User/Track?from_ar=1234567&to_ar=7654321&type=RECS_FOR_USER GET
---
> Track the action of a user.  
> type must be only 'RECS_FOR_USER' or 'RANKING'  
> Headers needed

*success response*
```
{}
```

/Article/MostViewed?number=10&offset=0 GET
---
> Get the most viewed articles across the whole site.  
> Headers needed

*success response*
```
{
  "Article": [
    {
      "type": "article",
      "id": 1493476013991,
      "ar_content": "上海的5A级景区不多，总共有3家，野生动物园是其中之一。动物园内汇集了世界各地具有代表性的动物和珍稀动物二百余种，地上爬的、天上飞的、水里游的应有尽有，数量上万，其中还有人民大众特别喜爱的长颈鹿",
      "ar_title": "上海野生动物园",
      "ar_place": "上海",
      "ar_category": "自然风光",
      "ar_like_list": "1493474492312;1492934467233;",
      "ar_collect_list": "",
      "ar_read_list": "1492934414312;1493474492312;1493474492312;",
      "ar_time_list": [
        {
          "id": 1493476013456,
          "ar_time": "2017-04-29 22:26:54.033"
        }
      ],
      "ar_url_list": [],
      "ar_author": {
        "id": 1493474492312,
        "u_name": "liangdong",
        "u_mail": "dl@sjtu.edu.cn",
        "u_password": "",
        "u_role": 1,
        "u_job": "Farm",
        "u_gender": 1,
        "u_age": 20
      },
      "ar_reviewer": [],
      "ar_review_list": [],
      "ar_tag_list": []
    }
  ]
}
```

/Article/SimilarRead?aid=1493476013991&number=10&offset=0 GET
---
> Get the recommended articles when a user is reading an article.  
> Headers needed

*success response*
```
{
  "Article": []
}
```