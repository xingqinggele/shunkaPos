package com.example.shunkapos.views;


import com.example.shunkapos.homefragment.hometeam.bean.TeamBean;

import java.util.Comparator;

/**
 * 作者: qgl
 * 创建日期：2021/1/19
 * 描述:以上升序
 */
public class Order2 implements Comparator<TeamBean> {
    @Override
    public int compare(TeamBean teamBean, TeamBean t1) {
        return (int) (Double.parseDouble(teamBean.getTeamTransAmount()) - Double.parseDouble(t1.getTeamTransAmount()));
    }
}
