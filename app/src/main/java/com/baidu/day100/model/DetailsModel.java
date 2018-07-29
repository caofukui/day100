package com.baidu.day100.model;

import android.util.Log;

import com.baidu.day100.bean.AddBean;
import com.baidu.day100.util.HttpUtils;
import com.baidu.day100.view.DetailsActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailsModel {
    private static final String TAG = DetailsActivity.class.getSimpleName();
    //http://www.zhaoapi.cn/product/addCart?uid=71&pid=6
    private String api="http://www.zhaoapi.cn/product/addCart?uid=71&pid=66";
    public void getDatas(final IDetailsModel iDetailsModel, String pid){
       // Log.i(TAG,api + pid);
        HttpUtils.doGet(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iDetailsModel.onSetError(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String string = response.body().string();
                Log.i(TAG,string);
                Gson gson=new Gson();
                AddBean addBean = gson.fromJson(string, AddBean.class);
                iDetailsModel.onSetSuccess(addBean);
            }
        });
    }
}
