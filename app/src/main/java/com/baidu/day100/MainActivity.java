package com.baidu.day100;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoFlowLayout autoFlowLayout;
    private ImageView iv_search;
    private ImageView iv_delete;
    private TextView tv_cancel;
    private EditText et_name;
    private String name;
    private List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setClick();
    }

    private void setClick() {
        iv_search.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initView() {
        list=new ArrayList<>();
        autoFlowLayout=findViewById(R.id.main_auto);
        iv_search=findViewById(R.id.header_img);
        iv_delete=findViewById(R.id.search_delete);
        tv_cancel=findViewById(R.id.header_cancel);
        et_name=findViewById(R.id.header_search);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_img:
                name = et_name.getText().toString();
                list.add(name);
                //添加流式布局效果
                addText();
                break;
            case R.id.search_delete:
                et_name.getText().clear();
                list.clear();
                autoFlowLayout.removeAllViews();
                break;
            case R.id.header_cancel:
                et_name.getText().clear();
                list.clear();
                break;
        }
    }

    private void addText() {
        autoFlowLayout.setAdapter(new FlowAdapter(list) {
            private View view;
            @Override
            public View getView(int i) {
                if (list!=null){
                    view = View.inflate(MainActivity.this, R.layout.auto_layout, null);
                    final TextView auto_Text=view.findViewById(R.id.auto_text);
                    auto_Text.setText(list.get(i));
                    auto_Text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取最近搜索中的点击内容进行传值
                            String s = auto_Text.getText().toString();
                            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                            intent.putExtra("name",s);
                            startActivity(intent);
                        }
                    });
                    list.clear();
                }
                return view;
            }
        });
    }
}
