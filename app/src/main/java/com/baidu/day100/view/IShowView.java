package com.baidu.day100.view;

import com.baidu.day100.bean.ShowBean;

public interface IShowView {
    void onSuccess(ShowBean showBean);
    void onError(int code);
}
