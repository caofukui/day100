package com.baidu.day100.presenter;

import com.baidu.day100.bean.CartBean;
import com.baidu.day100.model.CartModel;
import com.baidu.day100.model.ICartModel;
import com.baidu.day100.view.ICartView;
import com.baidu.day100.view.MyCartActivity;

public class CartPresenter implements ICartPresenter {
    private ICartView iCartView;
    private CartModel cartModel;
    public CartPresenter(ICartView iCartView) {
        this.iCartView=iCartView;
        this.cartModel=new CartModel();
    }
    public void setDatas(){
        cartModel.getDatas(new ICartModel() {
            @Override
            public void onSetSuccess(CartBean cartBean) {
                iCartView.onSuccess(cartBean);
            }

            @Override
            public void onSetError(int code) {
                iCartView.onError(code);
            }
        });
    }
    @Override
    public void onDestroys() {
        if (iCartView!=null){
            iCartView=null;
        }
    }
}
