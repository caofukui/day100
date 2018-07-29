package com.baidu.day100;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.day100.adapter.ShowAdapter;
import com.baidu.day100.bean.ShowBean;
import com.baidu.day100.presenter.ShowPresenter;
import com.baidu.day100.view.DetailsActivity;
import com.baidu.day100.view.IShowView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,IShowView {

    private String keywords;
    private ImageView iv_header_img;
    private ImageView header_close;
    private ImageView header_category;
    private ImageView show_back;
    private ImageView header_qrcode;
    private EditText header_search;
    private RadioGroup radioGroup;
    private RecyclerView recyclerView;
    private ShowPresenter showPresenter;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private int page=1;
    private int sort=0;
    private ShowAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        keywords = intent.getStringExtra("name");
        Toast.makeText(this,keywords,Toast.LENGTH_SHORT).show();
        initView();
        setData1();
        setClick();

    }

    private void setClick() {
        iv_header_img.setOnClickListener(this);
        header_close.setOnClickListener(this);
        header_category.setOnClickListener(this);
        header_qrcode.setOnClickListener(this);
        show_back.setOnClickListener(this);
        //radioGroup的点击事件
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setData1() {
        header_search.setText(keywords);

    }

    private void initView() {
        iv_header_img=findViewById(R.id.header_img02);
        header_close=findViewById(R.id.header_close);
        header_category=findViewById(R.id.header_category);
        header_qrcode=findViewById(R.id.header_qrcode);
        header_search=findViewById(R.id.header_search02);
        radioGroup=findViewById(R.id.show_rg);
        show_back=findViewById(R.id.show_back);
        recyclerView=findViewById(R.id.show_recycle);
        pullToRefreshScrollView=findViewById(R.id.show_pull);
        showPresenter = new ShowPresenter((IShowView) this);
        showPresenter.setDatas(keywords,page,sort);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this,LinearLayoutManager.VERTICAL,false));
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page=1;
                showPresenter.setDatas(keywords,page,sort);
                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                showPresenter.setDatas(keywords,page,sort);
                pullToRefreshScrollView.onRefreshComplete();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_img02:
                keywords=header_search.getText().toString();
                showPresenter.setDatas(keywords,page,sort);
                break;
            case R.id.header_close:
                header_search.getText().clear();
                break;
            case R.id.header_category:
                recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this,LinearLayoutManager.VERTICAL,false));
                header_category.setVisibility(View.GONE);
                header_qrcode.setVisibility(View.VISIBLE);
                break;
            case R.id.header_qrcode:
                recyclerView.setLayoutManager(new GridLayoutManager(ShowActivity.this,2));
                header_category.setVisibility(View.VISIBLE);
                header_qrcode.setVisibility(View.GONE);
                break;
            case R.id.show_back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb1:
                sort=0;
                showPresenter.setDatas(keywords,page,sort);
                break;
            case R.id.rb2:
                sort=1;
                showPresenter.setDatas(keywords,page,sort);
                break;
            case R.id.rb3:
                sort=2;
                showPresenter.setDatas(keywords,page,sort);
                break;
            case R.id.rb4:

                break;
        }
    }

    @Override
    public void onSuccess(final ShowBean showBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ShowAdapter(ShowActivity.this, showBean.getData());
                recyclerView.setAdapter(adapter);
                adapter.setmListener(new ShowAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(ShowActivity.this, DetailsActivity.class);
                        intent.putExtra("pid",showBean.getData().get(postion).getPid());
                        intent.putExtra("url",showBean.getData().get(postion).getDetailUrl());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onError(int code) {

    }

    @Override
    protected void onDestroy() {
        showPresenter.onDestroys();
        super.onDestroy();
    }
}
