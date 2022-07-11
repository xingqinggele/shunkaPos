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
import com.example.shunkapos.demo.demobean.MCityBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2021/1/25
 * 描述:
 */
public class MCityAdapter extends BaseAdapter {
    Context context;
    List<MCityBean> mCityList;
    private int cityIndex = -1;

    public MCityAdapter(Context context, List<MCityBean> mCityList) {
        this.context = context;
        this.mCityList = mCityList;
    }

    public int getSelectedPosition() {
        return this.cityIndex;
    }

    public void updateSelectedPosition(int index) {
        this.cityIndex = index;
    }

    public int getCount() {
        return this.mCityList.size();
    }

    public MCityBean getItem(int position) {
        return (MCityBean)this.mCityList.get(position);
    }

    public long getItemId(int position) {
        return Long.parseLong(((MCityBean)this.mCityList.get(position)).getAdrCode());
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

        MCityBean item = this.getItem(position);
        holder.name.setText(item.getAdrAbbreviation());
        boolean checked = this.cityIndex != -1 && ((MCityBean)this.mCityList.get(this.cityIndex)).getAdrAbbreviation().equals(item.getAdrAbbreviation());
        holder.name.setEnabled(!checked);
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
