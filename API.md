## Traveller API

User
===

/User/Login GET 
---
> Authenticate the user.

*request*
```
?u_name=tomcat&u_password=glassfish
```

*success response*
```
{
  "u_hash": -7654321,
  "u_name": "tomcat",
  "u_mail": "t@g.com",
  "u_role": 1
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
  "u_id":0,
  "u_name":"tomcat"
  "u_mail":"tom@xyz.com"
  "u_password":"tompwd"
  "u_role":0
}
```

*success response*
```
{
  "u_hash": 1042160000,
  "u_name": "tom",
  "u_mail": "tom@xyz.com",
  "u_role": 1
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
