package com.example.shunkapos.demo.demobean;

/**
 * 作者: qgl
 * 创建日期：2022/6/23
 * 描述:商户版我的商户
 */
public class MerchantMineChantsBean {
    private String id;
    private String status;
    private String subConfirm;
    private String name;
    private String externalId;
    private String smid;


    public String getSmid() {
        return smid;
    }

    public void setSmid(String smid) {
        this.smid = smid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubConfirm() {
        return subConfirm;
    }

    public void setSubConfirm(String subConfirm) {
        this.subConfirm = subConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}