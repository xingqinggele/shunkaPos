package com.example.shunkapos.demo.demobean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/15
 * 描述:省
 */
public class MProvinceBean implements Parcelable {

    private String id;
    private String adrIdentifier;
    private String adrAbbreviation;
    private String adrCode;
    private String adrPinyin;
    private String adrCname;
    private String adrName;
    private String adrParentCode;
    private String adrType;
    private List<MCityBean>mCityBeans ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdrIdentifier() {
        return adrIdentifier;
    }

    public void setAdrIdentifier(String adrIdentifier) {
        this.adrIdentifier = adrIdentifier;
    }

    public String getAdrAbbreviation() {
        return adrAbbreviation;
    }

    public void setAdrAbbreviation(String adrAbbreviation) {
        this.adrAbbreviation = adrAbbreviation;
    }

    public String getAdrCode() {
        return adrCode;
    }

    public void setAdrCode(String adrCode) {
        this.adrCode = adrCode;
    }

    public String getAdrPinyin() {
        return adrPinyin;
    }

    public void setAdrPinyin(String adrPinyin) {
        this.adrPinyin = adrPinyin;
    }

    public String getAdrCname() {
        return adrCname;
    }

    public void setAdrCname(String adrCname) {
        this.adrCname = adrCname;
    }

    public String getAdrName() {
        return adrName;
    }

    public void setAdrName(String adrName) {
        this.adrName = adrName;
    }

    public String getAdrParentCode() {
        return adrParentCode;
    }

    public void setAdrParentCode(String adrParentCode) {
        this.adrParentCode = adrParentCode;
    }

    public String getAdrType() {
        return adrType;
    }

    public void setAdrType(String adrType) {
        this.adrType = adrType;
    }

    public List<MCityBean> getmCityBeans() {
        return mCityBeans;
    }

    public void setmCityBeans(List<MCityBean> mCityBeans) {
        this.mCityBeans = mCityBeans;
    }

    protected MProvinceBean(Parcel in) {
        id = in.readString();
        adrIdentifier = in.readString();
        adrAbbreviation = in.readString();
        adrCode = in.readString();
        adrPinyin = in.readString();
        adrCname = in.readString();
        adrName = in.readString();
        adrParentCode = in.readString();
        adrType = in.readString();
        mCityBeans = in.createTypedArrayList(MCityBean.CREATOR);
    }

    public static final Creator<MProvinceBean> CREATOR = new Creator<MProvinceBean>() {
        @Override
        public MProvinceBean createFromParcel(Parcel in) {
            return new MProvinceBean(in);
        }

        @Override
        public MProvinceBean[] newArray(int size) {
            return new MProvinceBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(adrIdentifier);
        parcel.writeString(adrAbbreviation);
        parcel.writeString(adrCode);
        parcel.writeString(adrPinyin);
        parcel.writeString(adrCname);
        parcel.writeString(adrName);
        parcel.writeString(adrParentCode);
        parcel.writeString(adrType);
    }

    @Override
    public String toString() {
        return  this.adrAbbreviation;
    }
}