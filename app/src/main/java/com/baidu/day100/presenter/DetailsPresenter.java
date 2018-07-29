package com.baidu.day100.presenter;

import com.baidu.day100.bean.AddBean;
import com.baidu.day100.model.DetailsModel;
import com.baidu.day100.model.IDetailsModel;
import com.baidu.day100.view.DetailsActivity;
import com.baidu.day100.view.IDetailsView;

public class DetailsPresenter implements IDetailsPresenter {
    private IDetailsView iDetailsView;
    private DetailsModel detailsModel;
    public DetailsPresenter(IDetailsView iDetailsView) {
        this.iDetailsView=iDetailsView;
        this.detailsModel=new DetailsModel();
    }

    public void setDatas(String pid){
        detailsModel.getDatas(new IDetailsModel() {
            @Override
            public void onSetSuccess(AddBean addBean) {
                iDetailsView.onSuccess(addBean);
            }

            @Override
            public void onSetError(int code) {
                iDetailsView.onError(code);
            }
        },pid);
    }

    @Override
    public void onDestroys() {
        if (iDetailsView!=null){
            iDetailsView=null;
        }
    }
}
