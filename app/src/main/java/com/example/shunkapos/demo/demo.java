package com.example.shunkapos.demo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.homefragment.homeInvitepartners.HomeNewFilBean;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/7
 * 描述:
 */
public class demo extends BaseActivity implements View.OnClickListener, DemoAdapter.onEditClink {

    private List<HomeNewFilBean> mBean = new ArrayList<>();
    List<DemoBean>alist = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.demo;
    }

    @Override
    protected void initView() {


        newPosData();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    //获取类目
    private void newPosData() {
        RequestParams params = new RequestParams();
        params.put("userId", getUserId());
        HttpRequest.getEchoServer(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<HomeNewFilBean> homeNewFilBeans = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<HomeNewFilBean>>() {
                            }.getType());
                    shouLog("111----------->",alist.toString()+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });
    }

    @Override
    public void onClick(View view) {

        shouLog("--------->", mBean.size() + "");


    }

    @Override
    public void onEdit(String value, int position) {


    }


}