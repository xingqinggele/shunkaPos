package com.example.shunkapos.homefragment.homeInvitepartners;

/**
 * 作者: qgl
 * 创建日期：2022/3/12
 * 描述:
 */
public class HomeNewFilBean {
    private int id;
    private String serverName;
    private String serverMoney;
    private String serverType;
    private String num = "0";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerMoney() {
        return serverMoney;
    }

    public void setServerMoney(String serverMoney) {
        this.serverMoney = serverMoney;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "HomeNewFilBean{" +
                "id=" + id +
                ", serverName='" + serverName + '\'' +
                ", serverMoney='" + serverMoney + '\'' +
                ", serverType='" + serverType + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}