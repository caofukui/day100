package com.baidu.day100.view;

import com.baidu.day100.bean.AddBean;

public interface IDetailsView {
    public void onSuccess(AddBean addBean);
    public void onError(int code);
}
