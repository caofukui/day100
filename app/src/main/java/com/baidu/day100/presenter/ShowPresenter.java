package com.baidu.day100.presenter;

import com.baidu.day100.ShowActivity;
import com.baidu.day100.bean.ShowBean;
import com.baidu.day100.model.IShowModel;
import com.baidu.day100.model.ShowModel;
import com.baidu.day100.view.IShowView;

public class ShowPresenter implements IShowPresenter {
    private IShowView iShowView;
    private ShowModel showModel;
    public ShowPresenter(IShowView iShowView) {
        this.iShowView=iShowView;
        showModel=new ShowModel();
    }

    public void setDatas(String keywords, int page, int sort){
        showModel.getDatas(new IShowModel() {
            @Override
            public void onSetSuccess(ShowBean showBean) {
                iShowView.onSuccess(showBean);
            }

            @Override
            public void onSetError(int code) {
                iShowView.onError(code);
            }
        },keywords,page,sort);
    }

    @Override
    public void onDestroys() {
        if (iShowView!=null){
            iShowView=null;
        }
    }
}
