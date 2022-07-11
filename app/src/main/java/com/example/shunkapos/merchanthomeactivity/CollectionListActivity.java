package com.example.shunkapos.merchanthomeactivity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.homefragment.homemerchants.memerchants.activity.MeMerchantsActivity;
import com.example.shunkapos.homefragment.homemerchants.memerchants.activity.MeMerchantsDetailActivity;
import com.example.shunkapos.homefragment.homemerchants.memerchants.adapter.MeMerchantsAdapter;
import com.example.shunkapos.homefragment.homemerchants.memerchants.bean.MeMerchantsBean;
import com.example.shunkapos.merchanthomeadapter.CollectionListAdapter;
import com.example.shunkapos.merchanthomebean.CollectionListBean;
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
 * 创建日期：2022/7/8
 * 描述:收款记录
 */
public class CollectionListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    //返回键
    private LinearLayout iv_back;
    private SwipeRefreshLayout collection_swipe;
    private RecyclerView collection_list_view;
    private CollectionListAdapter mAdapter;
    private List<CollectionListBean> mBean = new ArrayList<>();
    //数据请求页码
    private int mCount = 1;
    // 请求数据数量
    private int pageSize = 20;

    private String smid = "";
    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.collectionlist_activity;
    }

    @Override
    protected void initView() {
        smid = getIntent().getStringExtra("smid");
        collection_swipe = findViewById(R.id.collection_swipe);
        collection_list_view = findViewById(R.id.collection_list_view);
        iv_back = findViewById(R.id.iv_back);
        initList();
    }

    //适配列表、刷新控件、adapter
    public void initList() {
        //下拉样式
        collection_swipe.setColorSchemeResources(R.color.new_theme_color, R.color.green, R.color.colorAccent);
        //上拉刷新初始化
        collection_swipe.setOnRefreshListener(this);
        //adapter配置data
        mAdapter = new CollectionListAdapter(R.layout.collection_list_item_view, mBean);
        //打开加载动画
        mAdapter.openLoadAnimation();
        //设置启用加载更多
        mAdapter.setEnableLoadMore(true);
        //设置为加载更多监听器
        mAdapter.setOnLoadMoreListener(this, collection_list_view);
        //数据为空显示xml
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        // RecyclerView设置布局管理器
        collection_list_view.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView配置adapter
        collection_list_view.setAdapter(mAdapter);
        //请求接口
        posDate(true);
    }

    /**
     * 请求数据
     *
     * @param isRefresh
     */
    private void posDate(boolean isRefresh) {
        RequestParams params = new RequestParams();
        params.put("smid", smid);
        params.put("pageNo", mCount + "");
        params.put("pageSize", pageSize + "");
        HttpRequest.PostPaymList(params, getToken(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //关闭加载条
                collection_swipe.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<CollectionListBean> memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("list").toString(),
                            new TypeToken<List<CollectionListBean>>() {
                            }.getType());
                    //判断刷新还是加载
                    if (isRefresh) {
                        //判断数组是否为空、为空不需要清空，不为空才需要清空
                        if (mBean != null) {
                            mBean.clear();
                        }
                    }
                    //在adapter List 中添加 list
                    mBean.addAll(memberList);
                    if (memberList.size() < pageSize) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                    //更新adapter
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //关闭加载条
                collection_swipe.setRefreshing(false);
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {
        //开启刷新
        collection_swipe.setRefreshing(true);
        //调用刷新逻辑
        setRefresh();
        //请求接口、填充数据
        posDate(true);
    }

    private void setRefresh() {
        //页码设置为1
        mCount = 1;
    }

    @Override
    public void onLoadMoreRequested() {
        //页码 n + 1
        mCount = mCount + 1;
        //请求接口、填充数据
        posDate(false);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}