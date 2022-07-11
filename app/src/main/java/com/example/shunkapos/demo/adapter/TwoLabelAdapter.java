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
import com.example.shunkapos.demo.demobean.TwoLabelBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2021/1/25
 * 描述:
 */
public class TwoLabelAdapter extends BaseAdapter {
    Context context;
    List<TwoLabelBean> mCityList;
    private int cityIndex = -1;

    public TwoLabelAdapter(Context context, List<TwoLabelBean> mCityList) {
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

    public TwoLabelBean getItem(int position) {
        return (TwoLabelBean)this.mCityList.get(position);
    }

    public long getItemId(int position) {
        return Long.parseLong(((TwoLabelBean)this.mCityList.get(position)).getId());
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

        TwoLabelBean item = this.getItem(position);
        holder.name.setText(item.getName());
        boolean checked = this.cityIndex != -1 && ((TwoLabelBean)this.mCityList.get(this.cityIndex)).getName().equals(item.getName());
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
