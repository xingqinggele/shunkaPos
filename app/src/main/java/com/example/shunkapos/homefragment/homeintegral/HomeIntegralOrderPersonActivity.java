package com.example.shunkapos.homefragment.homeintegral;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;

import com.example.shunkapos.homefragment.homeintegral.adpter.OrderPersonAdapter;
import com.example.shunkapos.homefragment.homeintegral.bean.OrderPersonBean;
import com.example.shunkapos.homefragment.homemerchants.memerchants.adapter.MeMerchantsTransferAdapter;
import com.example.shunkapos.homefragment.homemerchants.memerchants.bean.MeMerchantsTransferBean;
import com.example.shunkapos.homefragment.hometeam.bean.TeamBean;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.net.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2021/4/6
 * 描述: 积分兑换选择兑换对象
 */
public class HomeIntegralOrderPersonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView listView;
    //数据容器
    private List<OrderPersonBean> mData = new ArrayList<>();
    //适配器Adapter
    private OrderPersonAdapter mAdapter;
    //返回键
    private LinearLayout iv_back;
    //搜索框
    private EditText merchants_person_ed_search;
    //搜索内容
    private String search_value = "";
    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color,false).init();
        return R.layout.home_integral_order_person_activity;
    }

    @Override
    protected void initView() {
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        listView = findViewById(R.id.listview);
        iv_back = findViewById(R.id.iv_back);
        merchants_person_ed_search = findViewById(R.id.merchants_person_ed_search);
        search();
        initList();
    }

    //适配列表、刷新控件、adapter
    public void initList() {
        //下拉样式
        swipe_refresh_layout.setColorSchemeResources(R.color.new_theme_color, R.color.green, R.color.colorAccent);
        //上拉刷新初始化
        swipe_refresh_layout.setOnRefreshListener(this);
        //adapter配置data
        mAdapter = new OrderPersonAdapter(R.layout.item_home_integral_order_person, mData);
        //设置启用加载更多
        mAdapter.setEnableLoadMore(false);
        //设置为加载更多监听器
        mAdapter.setOnLoadMoreListener(this, listView);
        //数据为空显示xml
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        // RecyclerView设置布局管理器
        listView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView配置adapter
        listView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                i.putExtra("name", mData.get(position).getMerchName());
                i.putExtra("id", mData.get(position).getMerchId());
                setResult(3, i);
                finish();
            }
        });
        posData(search_value);
    }



    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        mData.clear();
        posData(search_value);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void posData(String value){
        RequestParams params = new RequestParams();
        params.put("message", value);
        HttpRequest.getTeamPersonList(params, getToken(), new ResponseCallback() {
            //成功回调
            @Override
            public void onSuccess(Object responseObj) {
                swipe_refresh_layout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<OrderPersonBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<OrderPersonBean>>() {
                            }.getType());

                        //判断数组是否为空、为空不需要清空，不为空才需要清空
                        if (mData != null){
                            mData.clear();
                        }
                    //在adapter List 中添加 list
                    mData.addAll(memberList);
                    mAdapter.loadMoreEnd();
                    //更新adapter
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //失败回调
            @Override
            public void onFailure(OkHttpException failuer) {
                swipe_refresh_layout.setRefreshing(false);
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });


    }


    private void search() {
        merchants_person_ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    Utils.hideKeyboard(merchants_person_ed_search);
                    search_value = v.getText().toString().trim();
                    onRefresh();
                    return true;
                }

                return false;
            }
        });
    }
}
