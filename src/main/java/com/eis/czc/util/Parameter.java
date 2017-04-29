package com.eis.czc.util;

import net.sf.json.JSONObject;

/**
 * Created by john on 2017/4/2 0002.
 */
public class Parameter {
    public static final String PREFIX114 ="http://120.77.42.242:8080/Entity/U1c1391d426af9b/Travellers/";
    //public static final String PREFIX114 ="http://112.74.62.114:8080/Entity/U192c20551af64/Travellers/";

    public static final String PREFIXREC ="http://202.120.40.139:8083/easyrec-web/api/1.1/json/";
    public static final String PREFIXREC1 ="http://202.120.40.139:8083/easyrec-web/api/1.0/";
    public static final String APIKEY = "0b9ce9a990539a81a0af01fec77dcc29";
    public static final String TENANTID = "Traveller";
    public static JSONObject authBody(JSONObject body){
        body.put("apikey",APIKEY);
        body.put("tenantid",TENANTID);
        return body;
    }
}
