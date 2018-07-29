package com.baidu.day100.model;

import com.baidu.day100.bean.AddBean;

public interface IDetailsModel {
    public void onSetSuccess(AddBean addBean);
    public void onSetError(int code);
}
