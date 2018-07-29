package com.baidu.day100.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.day100.R;
import com.baidu.day100.adapter.ExpandAdapter;
import com.baidu.day100.bean.CartBean;
import com.baidu.day100.presenter.CartPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity implements View.OnClickListener,ICartView {
    private ImageView iv_back;
    private ExpandableListView expandableListView;
    private CheckBox cart_check;
    private TextView cart_xuan;
    private TextView cart_price;
    private Button cart_xiaban;
    private CartPresenter cartPresenter;
    private ExpandAdapter expandAdapter;
    private List<CartBean.DataBean> list=new ArrayList<>();
    private boolean ischecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initView();
        setClick();
    }

    private void setClick() {
        iv_back.setOnClickListener(this);
        cart_check.setOnClickListener(this);
        cart_xiaban.setOnClickListener(this);
        //设置父条目不可点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true,表示不可点击
            }
        });

    }

    private void initView() {
        iv_back=findViewById(R.id.cart_back);
        expandableListView=findViewById(R.id.cart_expand);
        //设置属性去掉默认向下的箭头
        expandableListView.setGroupIndicator(null);
        cart_check=findViewById(R.id.cart_check);
        cart_xuan=findViewById(R.id.cart_xuan);
        cart_price=findViewById(R.id.cart_price);
        cart_xiaban=findViewById(R.id.cart_xiaban);
        cartPresenter=new CartPresenter(this);
        cartPresenter.setDatas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart_back:
                finish();
                break;
            case R.id.cart_check:
                //全选
                quanxuan();
                break;
            case R.id.cart_xiaban:

                break;
        }
    }

    private void quanxuan() {
        Toast.makeText(MyCartActivity.this,"点击了",Toast.LENGTH_SHORT).show();
        if (ischecked) {
            expandAdapter.setIsSelect(ischecked);
            ischecked = false;
            cart_check.setChecked(ischecked);
        } else {
            expandAdapter.setIsSelect(ischecked);
            ischecked = true;
            cart_check.setChecked(ischecked);
        }
    }

    @Override
    public void onSuccess(final CartBean cartBean) {
        list = cartBean.getData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                expandAdapter = new ExpandAdapter(MyCartActivity.this, cartBean.getData(), new ExpandAdapter.Callback() {
                    @Override
                    public void GoodCartsLitenster(int countyMoney, int countsNum) {
                        cart_price.setText("小计" + countyMoney);
                        cart_xuan.setText("已选（" + countsNum + ")");
                    }
                });
                expandableListView.setAdapter(expandAdapter);
                //设置子条目不点击展示
                int count = expandableListView.getCount();
                for (int i=0;i<count;i++){
                    expandableListView.expandGroup(i);
                }
            }
        });
    }

    @Override
    public void onError(int code) {

    }

    @Override
    protected void onDestroy() {
        cartPresenter.onDestroys();
        super.onDestroy();
    }
}
