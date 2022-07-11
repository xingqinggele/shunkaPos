package com.example.shunkapos.demo.model;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.demo.NewDemoActivity;
import com.example.shunkapos.demo.adapter.ThreeLabelAdapter;
import com.example.shunkapos.demo.adapter.TitleAdapter;
import com.example.shunkapos.demo.adapter.TwoLabelAdapter;
import com.example.shunkapos.demo.demobean.ThreeLabelBean;
import com.example.shunkapos.demo.demobean.TitleBean;
import com.example.shunkapos.demo.demobean.TwoLabelBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.adapter.AreaAdapter;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.CityBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.DistrictBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.ProvinceBean;
import com.example.shunkapos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述: 三级联动
 */
public class LabelView {
    private TitleAdapter titleAdapter;
    private TwoLabelAdapter twoLabelAdapter;
    private ThreeLabelAdapter threeLabelAdapter;
    private ListView mCityListView;
    private TextView mProTv;
    private TextView mCityTv;
    private TextView mAreaTv;
    private ImageView mCloseImg;
    private PopupWindow popWindow;
    private View mSelectedLine;
    private View popView;
    private int tabIndex = 0;
    private Context context;
    private List<TitleBean> titleBeans = new ArrayList<>();
    private List<TwoLabelBean> twoLabelBeans = null;
    private List<ThreeLabelBean> threeLabelBeans = null;
    private String colorSelected = "#ff181c20";
    private String colorAlert = "#ffff4444";
    private NewDemoActivity activity = null;

    public LabelView(Context context, NewDemoActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                case 0:
                    titleBeans = (List) msg.obj;
                    titleAdapter.notifyDataSetChanged();
                    mCityListView.setAdapter(titleAdapter);
                    break;
                case 1:
                    twoLabelBeans = (List) msg.obj;
                    twoLabelAdapter.notifyDataSetChanged();
                    if (twoLabelBeans != null && !twoLabelBeans.isEmpty()) {
                        mCityListView.setAdapter(twoLabelAdapter);
                        tabIndex = 1;
                    }
                    break;
                case 2:
                    threeLabelBeans = (List) msg.obj;
                    threeLabelAdapter.notifyDataSetChanged();
                    if (threeLabelBeans != null && !threeLabelBeans.isEmpty()) {
                        mCityListView.setAdapter(threeLabelAdapter);
                        tabIndex = 2;
                    }
            }
            updateTabsStyle(tabIndex);
            updateIndicator();
            return true;
        }
    });

    public void showCityPicker() {
        initJDCityPickerPop();
        if (!isShow()) {
            popWindow.showAtLocation(popView, 80, 0, 0);
        }
    }

    private boolean isShow() {
        return popWindow.isShowing();
    }

    public void hidePop() {
        if (isShow()) {
            popWindow.dismiss();
        }
    }

    private void initJDCityPickerPop() {
        tabIndex = 0;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popView = layoutInflater.inflate(R.layout.pop_jdcitypicker, (ViewGroup) null);
        mCityListView = (ListView) popView.findViewById(R.id.city_listview);
        mProTv = (TextView) popView.findViewById(R.id.province_tv);
        mCityTv = (TextView) popView.findViewById(R.id.city_tv);
        mAreaTv = (TextView) popView.findViewById(R.id.area_tv);
        mCloseImg = (ImageView) popView.findViewById(R.id.close_img);
        mSelectedLine = popView.findViewById(R.id.selected_line);
        popWindow = new PopupWindow(popView, -1, -2);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setBackgroundDrawable(new ColorDrawable());
        popWindow.setTouchable(true);
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                Utils.setBackgroundAlpha(context, 1.0F);
            }
        });
        mCloseImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hidePop();
                Utils.setBackgroundAlpha(context, 1.0F);
            }
        });
        mProTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tabIndex = 0;
                if (titleAdapter != null) {
                    mCityListView.setAdapter(titleAdapter);
                    if (titleAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(titleAdapter.getSelectedPosition());
                    }
                }

                updateTabVisible();
                updateIndicator();
            }
        });
        mCityTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tabIndex = 1;
                if (twoLabelAdapter != null) {
                    mCityListView.setAdapter(twoLabelAdapter);
                    if (twoLabelAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(twoLabelAdapter.getSelectedPosition());
                    }
                }

                updateTabVisible();
                updateIndicator();
            }
        });
        mAreaTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tabIndex = 2;
                if (threeLabelAdapter != null) {
                    mCityListView.setAdapter(threeLabelAdapter);
                    if (threeLabelAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(threeLabelAdapter.getSelectedPosition());
                    }
                }

                updateTabVisible();
                updateIndicator();
            }
        });
        mCityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedList(position);
            }
        });
        Utils.setBackgroundAlpha(context, 0.5F);
        updateIndicator();
        updateTabsStyle(-1);
        //请求一级类别
        activity.postMcc("0","0");
    }

    @SuppressLint("WrongConstant")
    private void updateTabVisible() {
        mProTv.setVisibility(titleBeans != null && !titleBeans.isEmpty() ? 0 : 8);
        mCityTv.setVisibility(twoLabelBeans != null && !twoLabelBeans.isEmpty() ? 0 : 8);
        mAreaTv.setVisibility(threeLabelBeans != null && !threeLabelBeans.isEmpty() ? 0 : 8);
    }

    private void updateIndicator() {
        popView.post(new Runnable() {
            public void run() {
                switch (tabIndex) {
                    case 0:
                        tabSelectedIndicatorAnimation(mProTv).start();
                        break;
                    case 1:
                        tabSelectedIndicatorAnimation(mCityTv).start();
                        break;
                    case 2:
                        tabSelectedIndicatorAnimation(mAreaTv).start();
                }

            }
        });
    }

    private AnimatorSet tabSelectedIndicatorAnimation(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(mSelectedLine, "X", new float[]{mSelectedLine.getX(), tab.getX()});
        final ViewGroup.LayoutParams params = mSelectedLine.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(new int[]{params.width, tab.getMeasuredWidth()});
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (Integer) animation.getAnimatedValue();
                mSelectedLine.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(new Animator[]{xAnimator, widthAnimator});
        return set;
    }

    private void selectedList(int position) {
        switch (tabIndex) {
            case 0:
                TitleBean provinceBean = titleAdapter.getItem(position);
                if (provinceBean != null) {
                    mProTv.setText("" + provinceBean.getName());
                    mCityTv.setText("请选择");
                    titleAdapter.updateSelectedPosition(position);
                    titleAdapter.notifyDataSetChanged();
                    //请求二级接口
                    activity.postMcc(provinceBean.getId(),"1");
                }
                break;
            case 1:
                TwoLabelBean cityBean = twoLabelAdapter.getItem(position);
                if (cityBean != null) {
                    mCityTv.setText("" + cityBean.getName());
                    mAreaTv.setText("请选择");
                    twoLabelAdapter.updateSelectedPosition(position);
                    twoLabelAdapter.notifyDataSetChanged();
                    //请求三级接口
                    activity.postMcc(cityBean.getId(),"2");
                }
                break;
            case 2:
                ThreeLabelBean districtBean = threeLabelAdapter.getItem(position);
                if (districtBean != null) {
                    callback(districtBean);
                }
        }

    }

    private void callback(ThreeLabelBean threeLabelBean) {
//        TitleBean titleBean = titleBeans != null && !titleBeans.isEmpty() && titleAdapter != null && titleAdapter.getSelectedPosition() != -1 ? (TitleBean) titleBeans.get(titleAdapter.getSelectedPosition()) : null;
//        TwoLabelBean twoLabelBean = twoLabelBeans != null && !twoLabelBeans.isEmpty() && twoLabelAdapter != null && twoLabelAdapter.getSelectedPosition() != -1 ? (TwoLabelBean) twoLabelBeans.get(twoLabelAdapter.getSelectedPosition()) : null;
        activity.setSelectCallback(""+threeLabelBean);
        if (threeLabelBean.getMccStatus().equals("1")){
            activity.setMccSatus(threeLabelBean.getMccStatus(),threeLabelBean.getIndustryQualificationType(),threeLabelBean.getMccMessage(),threeLabelBean.getMcc());
        }else {
            activity.setMccSatus(threeLabelBean.getMccStatus(),"","",threeLabelBean.getMcc());
        }
        hidePop();
    }

    @SuppressLint("WrongConstant")
    private void updateTabsStyle(int tabIndex) {
        switch (tabIndex) {
            case -1:
            case 0:
                mProTv.setTextColor(Color.parseColor(colorAlert));
                mProTv.setVisibility(0);
                mCityTv.setVisibility(8);
                mAreaTv.setVisibility(8);
                break;
            case 1:
                mProTv.setTextColor(Color.parseColor(colorSelected));
                mCityTv.setTextColor(Color.parseColor(colorAlert));
                mProTv.setVisibility(0);
                mCityTv.setVisibility(0);
                mAreaTv.setVisibility(8);
                break;
            case 2:
                mProTv.setTextColor(Color.parseColor(colorSelected));
                mCityTv.setTextColor(Color.parseColor(colorSelected));
                mAreaTv.setTextColor(Color.parseColor(colorAlert));
                mProTv.setVisibility(0);
                mCityTv.setVisibility(0);
                mAreaTv.setVisibility(0);
        }

    }

    public void setTitleData(List<TitleBean> titleList) {
            titleBeans = titleList;
            if (titleList != null && !titleList.isEmpty()) {
                titleAdapter = new TitleAdapter(context, titleList);
                mCityListView.setAdapter(titleAdapter);
            } else {
                Log.e("MainActivity.tshi", "解析本地城市数据失败！");
            }
    }

    public void setTwoLabelData(List<TwoLabelBean> twoLabelList) {
        twoLabelAdapter = new TwoLabelAdapter(context, twoLabelList);
        mHandler.sendMessage(Message.obtain(mHandler, 1, twoLabelList));
    }

    public void setThreeLabelData(List<ThreeLabelBean> threeLabelList) {
        threeLabelAdapter = new ThreeLabelAdapter(context, threeLabelList);
        mHandler.sendMessage(Message.obtain(mHandler, 2, threeLabelList));
    }
}