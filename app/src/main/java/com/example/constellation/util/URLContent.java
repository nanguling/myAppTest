package com.example.constellation.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLContent {

    //星座配对接口
    public static String getPairURL(String man,String woman) {
        man = man.replace("座","");
        woman = woman.replace("座","");
        try {
            man = URLEncoder.encode(man,"UTF-8");
            woman = URLEncoder.encode(woman,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://apis.juhe.cn/xzpd/query?men=" + man + "&women=" + woman + "&key=aab7f23b9a6149ef03e1b8136e38b640";
        return url;
    }

    //星座运势接口
    public static String getLuckURL(String starName) {
        try {
            starName = URLEncoder.encode(starName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://web.juhe.cn:8080/constellation/getAll?consName=" + starName + "&type=year&key=37654c72ae968cedc1f7f173181eb019";
        return url;
    }

}
