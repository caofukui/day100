package com.baidu.day100.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.day100.R;


public class SumLayout extends LinearLayout implements View.OnClickListener {
    private TextView sub;
    private TextView count;
    private TextView add;
    private OnDownSumLayouListener onDownSumLayoutListener;
    //自定义View的构造方法
    public SumLayout(Context context) {
        this(context, null);
    }

    public SumLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SumLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载视图
        View view = View.inflate(context, R.layout.sumlayout, this);
        //获取控件
        sub = view.findViewById(R.id.sub);
        count = view.findViewById(R.id.count);
        add = view.findViewById(R.id.add);
        //注册点击事件
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sub://数量减减
                //得到数量
                String ss = count.getText().toString();
                //转为int型
                int i = Integer.parseInt(ss);
                //减减
                i--;
                //判断当i大于等于0时给TextView赋值
                if (i >= 0) {
                    setCount(i + "");
                    //调用自定义接口点击事件
                    onDownSumLayoutListener.onDownSumLayout();
                }
                break;
            case R.id.add://数量加加
                //获取数量
                String s = count.getText().toString();
                //转为int型
                int i1 = Integer.parseInt(s);
                //数量加加
                i1++;
                //给数量TextView赋值
                setCount(i1 + "");
                //调用自定义接口点击事件
                onDownSumLayoutListener.onDownSumLayout();
                break;
        }
    }
    //给数量TextView赋值的方法
    public void setCount(String counts) {
        count.setText(counts);
    }
    //获取数量方法
    public String getCount() {
        return count.getText().toString();
    }
    //自定义接口点击事件
    public interface OnDownSumLayouListener {
        void onDownSumLayout();
    }
    //外部访问接口的方法
    public void setOnDownSumLayoutListener(OnDownSumLayouListener onDownSumLayoutListener) {
        this.onDownSumLayoutListener = onDownSumLayoutListener;
    }

}
