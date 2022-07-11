package com.example.shunkapos.demo;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述:
 */
public class NewDemoBean {
    private String id;
    private String merchantType;
    private String merchantName;
    private String certType;
    private String certNoMessage;
    private String nameMessage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNoMessage() {
        return certNoMessage;
    }

    public void setCertNoMessage(String certNoMessage) {
        this.certNoMessage = certNoMessage;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    @Override
    public String toString() {
        return  merchantName;
    }
}