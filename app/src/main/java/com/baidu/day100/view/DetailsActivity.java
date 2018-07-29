package com.baidu.day100.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.day100.R;
import com.baidu.day100.bean.AddBean;
import com.baidu.day100.presenter.DetailsPresenter;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,IDetailsView {

    private String pid;
    private String url;
    private WebView webView;
    private Button button;
    private DetailsPresenter detailsPresenter;
    private TextView de_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        Toast.makeText(DetailsActivity.this,pid,Toast.LENGTH_SHORT).show();
        url = pid = intent.getStringExtra("url");
        initView();

    }

    private void initView() {
        webView=findViewById(R.id.de_web);
        button=findViewById(R.id.de_add);
        de_cart=findViewById(R.id.de_cart);
        webView.loadUrl(url);
        button.setOnClickListener(this);
        de_cart.setOnClickListener(this);
        detailsPresenter=new DetailsPresenter((IDetailsView) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.de_add:
                detailsPresenter.setDatas(pid);
                break;
            case R.id.de_cart:
                startActivity(new Intent(DetailsActivity.this,MyCartActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        detailsPresenter.onDestroys();
        super.onDestroy();
    }

    @Override
    public void onSuccess(final AddBean addBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailsActivity.this,addBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError(int code) {

    }
}
