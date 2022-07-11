package com.example.shunkapos.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.demo.demobean.ThreeLabelBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.DistrictBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2021/1/25
 * 描述:
 */
public class ThreeLabelAdapter extends BaseAdapter {
    Context context;
    List<ThreeLabelBean> mDistrictList;
    private int districtIndex = -1;

    public ThreeLabelAdapter(Context context, List<ThreeLabelBean> mDistrictList) {
        this.context = context;
        this.mDistrictList = mDistrictList;
    }

    public int getSelectedPosition() {
        return this.districtIndex;
    }

    public void updateSelectedPosition(int index) {
        this.districtIndex = index;
    }

    public int getCount() {
        return this.mDistrictList.size();
    }

    public ThreeLabelBean getItem(int position) {
        return (ThreeLabelBean)this.mDistrictList.get(position);
    }

    public long getItemId(int position) {
        return Long.parseLong(((ThreeLabelBean)this.mDistrictList.get(position)).getId());
    }

    @SuppressLint("WrongConstant")
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_jdcitypicker_item, parent, false);
            holder = new Holder();
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.selectImg = (ImageView)convertView.findViewById(R.id.selectImg);
            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }

        ThreeLabelBean item = this.getItem(position);
        holder.name.setText(item.getName());
        boolean checked = this.districtIndex != -1 && ((ThreeLabelBean)this.mDistrictList.get(this.districtIndex)).getName().equals(item.getName());
        holder.name.setEnabled(!checked);
//        holder.selectImg.setVisibility(checked ? 0 : 8);
        holder.selectImg.setVisibility(checked ? 8 : 8);
        return convertView;
    }

    class Holder {
        TextView name;
        ImageView selectImg;

        Holder() {
        }
    }
}

