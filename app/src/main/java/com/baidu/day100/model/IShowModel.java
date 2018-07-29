package com.baidu.day100.model;

import com.baidu.day100.bean.ShowBean;

public interface IShowModel {
    void onSetSuccess(ShowBean showBean);
    void onSetError(int code);
}
