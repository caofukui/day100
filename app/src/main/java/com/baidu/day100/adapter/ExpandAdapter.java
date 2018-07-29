package com.baidu.day100.adapter;

import android.content.Context;
import android.util.Log;
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
    private static final String TAG = ExpandAdapter.class.getSimpleName();
    private Context context;
    private List<CartBean.DataBean> list;
    private Callback callback;
    //选中的货物的钱数
    private int countsMoney ;
    //选中的货物的个数
    private int countsNum ;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public ExpandAdapter(Context context, List<CartBean.DataBean> list,Callback callback) {
        this.context = context;
        this.list = list;
        this.callback=callback;
    }
    //将所有的单选框设为全选或全不选
    public void setIsSelect(boolean ischecked) {
        for (CartBean.DataBean li : list) {
            List<CartBean.DataBean.ListBean> list1 = li.getList();
            for (CartBean.DataBean.ListBean l : list1) {
                if (ischecked) {
                    l.setSelected(0);
                } else {
                    l.setSelected(1);
            }
            }
        }
        notifyDataSetChanged();
    }
    /**
     * 统计小计 获取选中的个数
     */
    public void getcountsMoneyAndNum() {
        countsMoney = 0;
        countsNum = 0;
        if (list == null) {
            return;
        }
        for (CartBean.DataBean li : list) {
            List<CartBean.DataBean.ListBean> list1 = li.getList();
            for (CartBean.DataBean.ListBean l : list1) {
                if (l.getSelected() == 1) {
                    countsMoney += (l.getNum() * l.getBargainPrice());
                    countsNum += l.getNum();
                }
            }
        }
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
        int selected = listBeans.get(childPosition).getSelected();
        if(selected==1){
            childViewHolder.checkBox.setChecked(true);
        }else{
            childViewHolder.checkBox.setChecked(false);
        }
        Glide.with(context).load(listBeans.get(childPosition).getImages().split("\\|")[0]).into(childViewHolder.imageView);
        childViewHolder.tvTitile.setText(listBeans.get(childPosition).getTitle());
        //因为是double类型所以要加“”，基本类中字段中有小数点都改成double类型
        childViewHolder.tvPrice.setText(listBeans.get(childPosition).getPrice()+"");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        getcountsMoneyAndNum();
        callback.GoodCartsLitenster(countsMoney,countsNum);
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


    public interface Callback {
        void GoodCartsLitenster(int countyMoney, int countsNum);
    }
}
