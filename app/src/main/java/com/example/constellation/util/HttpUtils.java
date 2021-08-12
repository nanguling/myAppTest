package com.example.constellation.util;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    public static String getJSONFromNet(String path) {
        String json = "";
        //将路径转化为URL对象
        try {
            URL url = new URL(path);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
