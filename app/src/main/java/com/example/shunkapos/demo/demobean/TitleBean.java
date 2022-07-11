package com.example.shunkapos.demo.demobean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述:一级列表
 */
public class TitleBean implements Parcelable {
    private String id;
    private String name;
    private String level;
    private ArrayList<TwoLabelBean> twoLabelList;

    public TitleBean() {

    }

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

    public ArrayList<TwoLabelBean> getTwoLabelList() {
        return twoLabelList;
    }

    public void setTwoLabelList(ArrayList<TwoLabelBean> twoLabelList) {
        this.twoLabelList = twoLabelList;
    }

    protected TitleBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        level = in.readString();
        twoLabelList = in.createTypedArrayList(TwoLabelBean.CREATOR);
    }

    public static final Creator<TitleBean> CREATOR = new Creator<TitleBean>() {
        @Override
        public TitleBean createFromParcel(Parcel in) {
            return new TitleBean(in);
        }

        @Override
        public TitleBean[] newArray(int size) {
            return new TitleBean[size];
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
        parcel.writeTypedList(twoLabelList);
    }

    @Override
    public String toString() {
        return  this.name;
    }


}
