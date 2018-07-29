package com.baidu.day100.model;

import com.baidu.day100.bean.ShowBean;
import com.baidu.day100.util.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowModel {
    private String api="http://www.zhaoapi.cn/product/searchProducts?keywords=";
    private String api2="&page=";
    private String api3="&sort=";



    public void getDatas(final IShowModel iShowModel, String keywords, int page, int sort){
        HttpUtils.doGet(api + keywords + api3 + sort, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iShowModel.onSetError(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                ShowBean showBean = gson.fromJson(string, ShowBean.class);
                iShowModel.onSetSuccess(showBean);
            }
        });
    }
}
