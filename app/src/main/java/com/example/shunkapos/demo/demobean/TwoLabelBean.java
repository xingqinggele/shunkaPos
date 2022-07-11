package com.example.shunkapos.demo.demobean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.CityBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.DistrictBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.ProvinceBean;

import java.util.ArrayList;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述:
 */
public class TwoLabelBean implements Parcelable {
    private String id;
    private String name;
    private String level;
    private ArrayList<ThreeLabelBean> threeLabelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<ThreeLabelBean> getThreeLabelList() {
        return threeLabelList;
    }

    public void setThreeLabelList(ArrayList<ThreeLabelBean> threeLabelList) {
        this.threeLabelList = threeLabelList;
    }

    protected TwoLabelBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        level = in.readString();
        threeLabelList = in.createTypedArrayList(ThreeLabelBean.CREATOR);
    }

    public static final Creator<TwoLabelBean> CREATOR = new Creator<TwoLabelBean>() {
        @Override
        public TwoLabelBean createFromParcel(Parcel in) {
            return new TwoLabelBean(in);
        }

        @Override
        public TwoLabelBean[] newArray(int size) {
            return new TwoLabelBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(level);
        parcel.writeTypedList(threeLabelList);
    }
    public String toString() {
        return this.name;
    }

}