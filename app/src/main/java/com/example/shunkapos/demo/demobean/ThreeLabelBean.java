package com.example.shunkapos.demo.demobean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述:3级列表
 */
public class ThreeLabelBean implements Parcelable {
    private String id;
    private String name;
    private String level;
    private String mccStatus;
    private String industryQualificationType;
    private String mccMessage;
    private String mcc;

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMccStatus() {
        return mccStatus;
    }

    public void setMccStatus(String mccStatus) {
        this.mccStatus = mccStatus;
    }

    public String getIndustryQualificationType() {
        return industryQualificationType;
    }

    public void setIndustryQualificationType(String industryQualificationType) {
        this.industryQualificationType = industryQualificationType;
    }

    public String getMccMessage() {
        return mccMessage;
    }

    public void setMccMessage(String mccMessage) {
        this.mccMessage = mccMessage;
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

    protected ThreeLabelBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        level = in.readString();
    }

    public static final Creator<ThreeLabelBean> CREATOR = new Creator<ThreeLabelBean>() {
        @Override
        public ThreeLabelBean createFromParcel(Parcel in) {
            return new ThreeLabelBean(in);
        }

        @Override
        public ThreeLabelBean[] newArray(int size) {
            return new ThreeLabelBean[size];
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
    }
    public String toString() {
        return this.name;
    }


}