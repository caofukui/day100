package com.baidu.day100.model;

import com.baidu.day100.bean.CartBean;
import com.baidu.day100.util.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CartModel {
    //*****一定要把接口中的int类型的数据转换成String类型
    private String api="http://www.zhaoapi.cn/product/getCarts?uid=71";
    public void getDatas(final ICartModel iCartModel){
        HttpUtils.doGet(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iCartModel.onSetError(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                CartBean cartBean = gson.fromJson(string, CartBean.class);
                iCartModel.onSetSuccess(cartBean);
            }
        });
    }
}
