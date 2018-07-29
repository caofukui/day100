package com.baidu.day100.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtils {
    private static OkHttpClient client=null;
    private static final HttpUtils ourInstance = new HttpUtils();

    public static OkHttpClient getInstance() {
        if (client==null){
            synchronized (HttpUtils.class){
                if (client==null){
                    client=new OkHttpClient();
                }
            }
        }
        return client;
    }

    private HttpUtils() {

    }


    public static void doGet(String url, Callback callback) {
        Request request=new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
