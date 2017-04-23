package com.eis.czc.util;

import net.sf.json.JSONObject;

/**
 * Created by john on 2017/4/2 0002.
 */
public class Parameter {
    public static final String PREFIX114 ="http://120.77.42.242:8080/Entity/U1c1391d426af9b/Travellers/";
    //public static final String PREFIX114 ="http://112.74.62.114:8080/Entity/U192c20551af64/Travellers/";

    public static final String[] JOBS = {"一般职�?, "农牧�?, "渔业", "木材、森林业", "矿业、采石业", "交通运输业", "餐旅�?,
            "建筑工程�?, "制造业", "新闻、出版、广告业", "卫生", "娱乐�?, "文教", "宗教", "公共事业", "商业", "金融�?,
            "服务�?, "家庭管理", "治安人员", "军人", "体育", "资讯", "其它"};

    public static final String PREFIXREC ="http://59.78.45.250��8071/easyrec-web/api/1.1/json";
    public static final String APIKEY = "0b9ce9a990539a81a0af01fec77dcc29";
    public static final String TENANTID = "Traveller";
    public static JSONObject authBody(JSONObject body){
        body.put("apikey",APIKEY);
        body.put("tenantid",TENANTID);
        return body;
    }
}
