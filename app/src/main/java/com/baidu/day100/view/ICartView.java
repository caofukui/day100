package com.baidu.day100.view;

import com.baidu.day100.bean.CartBean;

public interface ICartView {
    void onSuccess(CartBean cartBean);
    void onError(int code);
}
