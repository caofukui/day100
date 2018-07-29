package com.baidu.day100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.day100.R;
import com.baidu.day100.bean.CartBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CartBean.DataBean> list;

    public ExpandAdapter(Context context, List<CartBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder=null;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.group_item,parent,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.textView=convertView.findViewById(R.id.group_title);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.textView.setText(list.get(groupPosition).getSellerName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.child_item,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.checkBox=convertView.findViewById(R.id.child_box);
            childViewHolder.imageView=convertView.findViewById(R.id.child_img);
            childViewHolder.tvTitile=convertView.findViewById(R.id.child_title);
            childViewHolder.tvPrice=convertView.findViewById(R.id.child_price);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        List<CartBean.DataBean.ListBean> listBeans = this.list.get(groupPosition).getList();
        String selected = listBeans.get(childPosition).getSelected();
        if(selected.equals("1")){
            childViewHolder.checkBox.setChecked(true);
        }else{
            childViewHolder.checkBox.setChecked(false);
        }
        Glide.with(context).load(listBeans.get(childPosition).getImages().split("\\|")[0]).into(childViewHolder.imageView);
        childViewHolder.tvTitile.setText(listBeans.get(childPosition).getTitle());
        childViewHolder.tvPrice.setText(listBeans.get(childPosition).getPrice());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public class GroupViewHolder{
        TextView textView;
    }
    public class ChildViewHolder{
        CheckBox checkBox;
        ImageView imageView;
        TextView tvTitile;
        TextView tvPrice;
    }
}
