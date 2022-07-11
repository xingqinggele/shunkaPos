package com.example.shunkapos.demo;

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
import com.example.shunkapos.demo.adapter.MerchantMineChantsAdapter;
import com.example.shunkapos.demo.demobean.MerchantMineChantsBean;
import com.example.shunkapos.homefragment.homemerchants.memerchants.activity.MeMerchantsActivity;
import com.example.shunkapos.homefragment.homemerchants.memerchants.activity.MeMerchantsDetailActivity;
import com.example.shunkapos.homefragment.homemerchants.memerchants.adapter.MeMerchantsAdapter;
import com.example.shunkapos.homefragment.homemerchants.memerchants.bean.MeMerchantsBean;
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
 * 创建日期：2022/6/23
 * 描述:商户版我的商户
 */
public class MerchantMineChantsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener, MerchantMineChantsAdapter.ChecksTandCallback {
    private SwipeRefreshLayout merchants_swipe;
    private RecyclerView merchants_list_view;
    private MerchantMineChantsAdapter adapter;
    private List<MerchantMineChantsBean>mData = new ArrayList<>();
    private EditText merchants_searchField_ed;
    //数据请求页码
    private int mCount = 1;
    // 请求数据数量
    private int pageSize = 20;
    //搜索条件
    private String searchField = "";
    private TextView merchants_list_size;
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color,false).init();
        return R.layout.merchant_mine_chants_activity;
    }

    @Override
    protected void initView() {
        merchants_swipe = findViewById(R.id.merchants_swipe);
        merchants_list_view = findViewById(R.id.merchants_list_view);
        merchants_list_size = findViewById(R.id.merchants_list_size);
        merchants_searchField_ed = findViewById(R.id.merchants_searchField_ed);
        iv_back = findViewById(R.id.iv_back);
        initList();
        search();
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    //适配列表、刷新控件、adapter
    public void initList() {
        //下拉样式
        merchants_swipe.setColorSchemeResources(R.color.new_theme_color, R.color.green, R.color.colorAccent);
        //上拉刷新初始化
        merchants_swipe.setOnRefreshListener(this);
        //adapter配置data
        adapter = new MerchantMineChantsAdapter(R.layout.item_new_merchats, mData,this);
        //打开加载动画
        adapter.openLoadAnimation();
        //设置启用加载更多
        adapter.setEnableLoadMore(true);
        //设置为加载更多监听器
        adapter.setOnLoadMoreListener(this, merchants_list_view);
        //数据为空显示xml
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        // RecyclerView设置布局管理器
        merchants_list_view.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView配置adapter
        merchants_list_view.setAdapter(adapter);
        //RecyclerView点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        //请求接口
        posDate(true);
    }

    //接口获取数据
    public void posDate(boolean isRefresh) {
        RequestParams params = new RequestParams();
        params.put("userId", getmUserId());
        params.put("pageNo", mCount + "");
        params.put("pageSize", pageSize + "");
        params.put("searchField", searchField);
        HttpRequest.PosIncomingList(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //关闭加载条
                merchants_swipe.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = result.getJSONObject("data");
                    List<MerchantMineChantsBean> memberList = gson.fromJson(data.getJSONArray("list").toString(),
                            new TypeToken<List<MerchantMineChantsBean>>() {
                            }.getType());
                    //判断刷新还是加载
                    if (isRefresh){
                        //判断数组是否为空、为空不需要清空，不为空才需要清空
                        if (mData != null){
                            mData.clear();
                        }
                    }
                    //在adapter List 中添加 list
                    mData.addAll(memberList);
                    merchants_list_size.setText("共" + data.getString("totals") + "户");
                    if (memberList.size() < pageSize) {
                        adapter.loadMoreEnd();
                    } else {
                        adapter.loadMoreComplete();
                    }
                    //更新adapter
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //关闭加载条
                merchants_swipe.setRefreshing(false);
                Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });
    }

    @Override
    public void onRefresh() {
        merchants_swipe.setRefreshing(true);
        mCount = 1;
        posDate(true);
    }

    @Override
    public void onLoadMoreRequested() {
        //页码 n + 1
        mCount = mCount + 1;
        //请求接口、填充数据
        posDate(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onRefresh();
    }

    //搜索框
    private void search() {
        merchants_searchField_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    Utils.hideKeyboard(merchants_searchField_ed);
                    //输入内容赋给搜索内容
                    searchField = v.getText().toString().trim();
                    //访问接口
                    onRefresh();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void addAction(String id,String name) {
        Intent intent = new Intent(this, CheckstandActivity.class);
        intent.putExtra("smid",id);
        intent.putExtra("name",name);
        startActivity(intent);

    }
}