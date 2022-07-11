package com.example.shunkapos.demo;

import com.example.shunkapos.homefragment.homeInvitepartners.HomeNewFilBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/9
 * 描述:
 */
public class DemoBean {

    private String serverType;
    private List<HomeNewFilBean> beans = new ArrayList<>();

    public DemoBean() {

    }

    public DemoBean(String serverType, List<HomeNewFilBean> beans) {
        this.serverType = serverType;
        this.beans = beans;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public List<HomeNewFilBean> getBeans() {
        return beans;
    }

    public void setBeans(List<HomeNewFilBean> beans) {
        this.beans = beans;
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "serverType='" + serverType + '\'' +
                ", beans=" + beans +
                '}';
    }
}