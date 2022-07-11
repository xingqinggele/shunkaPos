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
import com.example.shunkapos.demo.demobean.TitleBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.ProvinceBean;

import java.util.List;


/**
 * 作者: qgl
 * 创建日期：2021/1/25
 * 描述:
 */
public class TitleAdapter extends BaseAdapter {
    Context context;
    List<TitleBean> mProList;
    private int provinceIndex = -1;

    public TitleAdapter(Context context, List<TitleBean> mProList) {
        this.context = context;
        this.mProList = mProList;
    }

    public void updateSelectedPosition(int index) {
        this.provinceIndex = index;
    }

    public int getSelectedPosition() {
        return this.provinceIndex;
    }

    public int getCount() {
        return this.mProList.size();
    }

    public TitleBean getItem(int position) {
        return (TitleBean)this.mProList.get(position);
    }

    public long getItemId(int position) {
        return Long.parseLong(((TitleBean)this.mProList.get(position)).getId());
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

        TitleBean item = this.getItem(position);
        holder.name.setText(item.getName());
        boolean checked = this.provinceIndex != -1 && ((TitleBean)this.mProList.get(this.provinceIndex)).getName().equals(item.getName());
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

