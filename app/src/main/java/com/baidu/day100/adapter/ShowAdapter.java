package com.baidu.day100.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.day100.R;
import com.baidu.day100.bean.ShowBean;
import com.baidu.day100.view.DetailsActivity;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {
    private Context context;
    private List<ShowBean.DataBean> list;
    private View view;
    private OnItemClickListener mListener;// 声明自定义的接口

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public ShowAdapter(Context context, List<ShowBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, final int position) {
        //Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(holder.iv_img);
        ImageLoader.getInstance().displayImage(list.get(position).getImages().split("\\|")[0],holder.iv_img);
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_price.setText("¥"+list.get(position).getPrice());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("pid",list.get(position).getPid());
                intent.putExtra("url",list.get(position).getDetailUrl());
                context.startActivity(intent);*/
                // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_title;
        TextView tv_price;
        public ShowViewHolder(View itemView) {
            super(itemView);
            iv_img=itemView.findViewById(R.id.item1_img);
            tv_title=itemView.findViewById(R.id.item1_title);
            tv_price=itemView.findViewById(R.id.item1_price);
        }
    }
    //自定义接口
    public interface OnItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
