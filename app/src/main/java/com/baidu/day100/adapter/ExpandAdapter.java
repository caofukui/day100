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
import com.baidu.day100.view.SumLayout;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.child_item,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.checkBox=convertView.findViewById(R.id.child_box);
            childViewHolder.imageView=convertView.findViewById(R.id.child_img);
            childViewHolder.tvTitile=convertView.findViewById(R.id.child_title);
            childViewHolder.tvPrice=convertView.findViewById(R.id.child_price);
            childViewHolder.sumLayout = convertView.findViewById(R.id.cart_childe_item_sumlayout);
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

        final ChildViewHolder finalMyChildView = childViewHolder;
        /**
         * 自定义加减框控件点击事件
         */
        childViewHolder.sumLayout.setOnDownSumLayoutListener(new SumLayout.OnDownSumLayouListener() {
            @Override
            public void onDownSumLayout() {
                //得到改变后的购买数量
                String count = finalMyChildView.sumLayout.getCount();
                //转为int型
                int i = Integer.parseInt(count);
                //并重新给子条目数据的购买数量赋值
                list.get(groupPosition).getList().get(childPosition).setNum(i);
                //刷新适配器，使显示器上的值改变
                notifyDataSetChanged();

            }
        });

        childViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = list.get(groupPosition).getList().get(childPosition).getSelected();
                if (selected == 0) {
                    list.get(groupPosition).getList().get(childPosition).setSelected(1);
                } else if (selected == 1) {
                    list.get(groupPosition).getList().get(childPosition).setSelected(0);
                }
                notifyDataSetChanged();
            }
        });
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
        SumLayout sumLayout;
    }


    public interface Callback {
        void GoodCartsLitenster(int countyMoney, int countsNum);
    }
}
