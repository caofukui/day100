package com.baidu.day100.model;

import com.baidu.day100.bean.CartBean;

public interface ICartModel {
    void onSetSuccess(CartBean cartBean);
    void onSetError(int code);
}
