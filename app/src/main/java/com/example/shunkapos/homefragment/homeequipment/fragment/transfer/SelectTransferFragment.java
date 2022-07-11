package com.example.shunkapos.homefragment.homeequipment.fragment.transfer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseFragment;
import com.example.shunkapos.homefragment.homeequipment.activity.TerminalTransferActivity;
import com.example.shunkapos.homefragment.homeequipment.activity.TransferPersonActivity;
import com.example.shunkapos.homefragment.homeequipment.adapter.ChooserRecyclerAdapter;
import com.example.shunkapos.homefragment.homeequipment.adapter.RecyclerViewItemDecoration;
import com.example.shunkapos.homefragment.homeequipment.bean.TerminalEvenBusBean;
import com.example.shunkapos.homefragment.homeequipment.bean.TerminalBean;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.net.Utils;
import com.example.shunkapos.utils.SPUtils;
import com.example.shunkapos.views.MyDialog1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者: qgl
 * 创建日期：2020/12/24
 * 描述:选择划拨
 */
public class SelectTransferFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView tv1;
    private RecyclerView listview;
    private RecyclerViewItemDecoration recyclerViewItemDecoration;
    private ChooserRecyclerAdapter manyRecyclerAdapter;
    private CheckBox check_box;
    private boolean isType = false;
    private TextView tv;
    private TextView check_box_type;
    private TextView tv_number1;
    private Button bt_sub;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout appBarLayout;
    //列表Bean
    private List<TerminalBean> beans = new ArrayList<>();
    private List<TerminalBean> beanList_size = new ArrayList<>();
    protected Context mContext;
    //搜索框
    private EditText merchants_query_ed_search;
    private String sertch_value = "";
    //pos类型
    private String posType = "";


    @Override
    protected void initView(View rootView) {
        mContext = getActivity();
        EventBus.getDefault().register(this);
        recyclerViewItemDecoration = new RecyclerViewItemDecoration(getActivity(), 1);
        appBarLayout = rootView.findViewById(R.id.appBarLayout);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        listview = rootView.findViewById(R.id.listview);
        tv = rootView.findViewById(R.id.merchants_transfer_number);
        check_box = rootView.findViewById(R.id.check_box);
        tv1 = rootView.findViewById(R.id.tv1);
        tv_number1 = rootView.findViewById(R.id.tv_number1);
        bt_sub = rootView.findViewById(R.id.bt_sub);
        check_box_type = rootView.findViewById(R.id.check_box_type);
        merchants_query_ed_search = rootView.findViewById(R.id.merchants_query_ed_search);
        tv1.setOnClickListener(this);
        bt_sub.setOnClickListener(this);
        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!beans.isEmpty()) {
                        if (isType) {
                            manyRecyclerAdapter.setAllSelect();
                            isType = false;
                            tv.setText("总计:" + 0 + "台");
                        } else {
                            isType = true;
                            manyRecyclerAdapter.getAllSelect();
                            tv.setText("总计:" + beans.size() + "台");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        ini();
        search();
    }

    @Override
    protected int getLayoutInflaterResId() {
        return R.layout.select_transfer_fragment;
    }

    public void ini() {
        //下拉样式
        mSwipeRefreshLayout.setColorSchemeResources(R.color.new_theme_color, R.color.green, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listview.addItemDecoration(recyclerViewItemDecoration);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));

        postData();
    }

    /*************Adapter接口回调********************/
    ChooserRecyclerAdapter.OnAddClickListener onItemActionClick = new ChooserRecyclerAdapter.OnAddClickListener() {
        @Override
        public void onItemClick() {
            Log.e("啊", "走了");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int len = 0;
                        int lenght = beans.size();
                        if (lenght >= 1) {
                            for (int i = 0; i < lenght; i++) {
                                if (manyRecyclerAdapter.ischeck.get(i, false)) {
                                    len = len + 1;
                                }
                            }
                            tv.setText("总计:" + len + "台");
                            if (len == 0) {
                                isType = false;
                                check_box_type.setText("全选");
                                check_box.setChecked(false);

                            } else if (len > 0 & len < lenght) {
                                isType = false;
                                check_box_type.setText("全选");
                                check_box.setChecked(false);

                            } else if (len == lenght) {
                                isType = true;
                                check_box_type.setText("取消");
                                check_box.setChecked(true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        tv.setText("总计:" + "0" + "台");
        check_box.setChecked(false);
        beans.clear();
        beanList_size.clear();
        sertch_value = "";
        postData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                ((TerminalTransferActivity) getActivity()).setListSize(1);
                break;
            case R.id.bt_sub:
                StringBuffer sb = new StringBuffer();
                beanList_size = new ArrayList<>();
                //获取选中的数据
                for (int i = 0; i < beans.size(); i++) {
                    if (manyRecyclerAdapter.ischeck.get(i, false)) {
                        sb.append(beans.get(i).getPosId().toString());
                        beanList_size.add(beans.get(i));
                    }
                }
                if (sb.toString().equals("")) {
                    Toast.makeText(getActivity(), "请选择要划拨的机器", Toast.LENGTH_LONG).show();
                } else {
                        showDialog();
                }
                break;
        }
    }

    public void postData() {
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(getActivity(), "userId", "-1").toString());
        params.put("posActivateStatus", "0");
        params.put("pageNo", "1");
        params.put("pageSize", "10");
        params.put("posCode", sertch_value);
        params.put("operType", "1"); // 1. 划拨 2.回调
        params.put("posType", posType);
        HttpRequest.getEquipmentList(params, SPUtils.get(getActivity(), "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<TerminalBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<TerminalBean>>() {
                            }.getType());
                    beans.addAll(memberList);
                    tv_number1.setText(memberList.size() + "");
                    manyRecyclerAdapter = new ChooserRecyclerAdapter(beans, getActivity());
                    listview.setAdapter(manyRecyclerAdapter);
                    manyRecyclerAdapter.setOnAddClickListener(onItemActionClick);
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

    // 可划拨弹框
    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_fragment, null);
        TextView textView = view.findViewById(R.id.dialog_tv1);
        TextView dialog_cancel = view.findViewById(R.id.dialog_cancel);
        TextView dialog_determine = view.findViewById(R.id.dialog_determine);
        textView.setText("共" + beanList_size.size() + "台,可划拨" + beanList_size.size() + "台");
        Dialog dialog = new MyDialog1(getActivity(), true, true, (float) 0.7).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
                //跳转到人员选择界面
                Intent intent = new Intent(getActivity(), TransferPersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("beanList_size", (Serializable) beanList_size);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //搜索框
    private void search() {
        merchants_query_ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    Utils.hideKeyboard(merchants_query_ed_search);
                    sertch_value = v.getText().toString();
                    tv.setText("总计:" + "0" + "台");
                    beans.clear();
                    beanList_size.clear();
                    postData();
                    return true;
                }
                return false;
            }
        });
    }

    public void onEventMainThread(SelectTransferFragment ev) {
        shouLog("走了吗", "走； ");
        onRefresh();
    }

    public void onEventMainThread(TerminalEvenBusBean busBean){
        shouLog("选择划拨终端类型",busBean.getTerminalType());
        posType = busBean.getTerminalType();
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }


}
