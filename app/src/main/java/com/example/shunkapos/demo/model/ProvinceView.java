package com.example.shunkapos.demo.model;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import com.example.shunkapos.demo.NewDemoActivity02;
import com.example.shunkapos.demo.adapter.MAreaAdapter;
import com.example.shunkapos.demo.adapter.MCityAdapter;
import com.example.shunkapos.demo.adapter.MProvinceAdapter;
import com.example.shunkapos.demo.adapter.ThreeLabelAdapter;
import com.example.shunkapos.demo.adapter.TitleAdapter;
import com.example.shunkapos.demo.adapter.TwoLabelAdapter;
import com.example.shunkapos.demo.demobean.MAreaBean;
import com.example.shunkapos.demo.demobean.MCityBean;
import com.example.shunkapos.demo.demobean.MProvinceBean;
import com.example.shunkapos.demo.demobean.ThreeLabelBean;
import com.example.shunkapos.demo.demobean.TitleBean;
import com.example.shunkapos.demo.demobean.TwoLabelBean;
import com.example.shunkapos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述: 三级联动
 */
public class ProvinceView {
    private MProvinceAdapter mProvinceAdapter;
    private MCityAdapter mCityAdapter;
    private MAreaAdapter mAreaAdapter;
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
    private List<MProvinceBean> titleBeans = new ArrayList<>();
    private List<MCityBean> twoLabelBeans = null;
    private List<MAreaBean> threeLabelBeans = null;
    private String colorSelected = "#ff181c20";
    private String colorAlert = "#ffff4444";
    private NewDemoActivity02 activity = null;

    public ProvinceView(Context context, NewDemoActivity02 activity) {
        this.context = context;
        this.activity = activity;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                case 0:
                    titleBeans = (List) msg.obj;
                    mProvinceAdapter.notifyDataSetChanged();
                    mCityListView.setAdapter(mProvinceAdapter);
                    break;
                case 1:
                    twoLabelBeans = (List) msg.obj;
                    mCityAdapter.notifyDataSetChanged();
                    if (twoLabelBeans != null && !twoLabelBeans.isEmpty()) {
                        mCityListView.setAdapter(mCityAdapter);
                        tabIndex = 1;
                    }
                    break;
                case 2:
                    threeLabelBeans = (List) msg.obj;
                    mAreaAdapter.notifyDataSetChanged();
                    if (threeLabelBeans != null && !threeLabelBeans.isEmpty()) {
                        mCityListView.setAdapter(mAreaAdapter);
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
                if (mProvinceAdapter != null) {
                    mCityListView.setAdapter(mProvinceAdapter);
                    if (mProvinceAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(mProvinceAdapter.getSelectedPosition());
                    }
                }

                updateTabVisible();
                updateIndicator();
            }
        });
        mCityTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tabIndex = 1;
                if (mCityAdapter != null) {
                    mCityListView.setAdapter(mCityAdapter);
                    if (mCityAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(mCityAdapter.getSelectedPosition());
                    }
                }

                updateTabVisible();
                updateIndicator();
            }
        });
        mAreaTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tabIndex = 2;
                if (mAreaAdapter != null) {
                    mCityListView.setAdapter(mAreaAdapter);
                    if (mAreaAdapter.getSelectedPosition() != -1) {
                        mCityListView.setSelection(mAreaAdapter.getSelectedPosition());
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
        activity.postProvince("1","2",0);
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
                MProvinceBean provinceBean = mProvinceAdapter.getItem(position);
                if (provinceBean != null) {
                    mProTv.setText("" + provinceBean.getAdrAbbreviation());
                    mCityTv.setText("请选择");
                    mProvinceAdapter.updateSelectedPosition(position);
                    mProvinceAdapter.notifyDataSetChanged();
                    //请求二级接口
                    activity.postProvince(provinceBean.getAdrCode(),"3",1);
                }
                break;
            case 1:
                MCityBean cityBean = mCityAdapter.getItem(position);
                if (cityBean != null) {
                    mCityTv.setText("" + cityBean.getAdrAbbreviation());
                    mAreaTv.setText("请选择");
                    mCityAdapter.updateSelectedPosition(position);
                    mCityAdapter.notifyDataSetChanged();
                    //请求三级接口
                    activity.postProvince(cityBean.getAdrCode(),"4",2);
                }
                break;
            case 2:
                MAreaBean districtBean = mAreaAdapter.getItem(position);
                if (districtBean != null) {
                    callback(districtBean);
                }
        }

    }

    private void callback(MAreaBean threeLabelBean) {
        MProvinceBean titleBean = titleBeans != null && !titleBeans.isEmpty() && mProvinceAdapter != null && mProvinceAdapter.getSelectedPosition() != -1 ? (MProvinceBean) titleBeans.get(mProvinceAdapter.getSelectedPosition()) : null;
        MCityBean twoLabelBean = twoLabelBeans != null && !twoLabelBeans.isEmpty() && mCityAdapter != null && mCityAdapter.getSelectedPosition() != -1 ? (MCityBean) twoLabelBeans.get(mCityAdapter.getSelectedPosition()) : null;
        activity.setSelectAdrCallback(titleBean+"-"+twoLabelBean+"-"+threeLabelBean, titleBean.getAdrCode(),twoLabelBean.getAdrCode(), threeLabelBean.getAdrCode());
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

    public void setTitleData(List<MProvinceBean> titleList) {
            titleBeans = titleList;
            if (titleList != null && !titleList.isEmpty()) {
                mProvinceAdapter = new MProvinceAdapter(context, titleList);
                mCityListView.setAdapter(mProvinceAdapter);
            } else {
                Log.e("MainActivity.tshi", "解析本地城市数据失败！");
            }
    }

    public void setTwoLabelData(List<MCityBean> twoLabelList) {
        mCityAdapter = new MCityAdapter(context, twoLabelList);
        mHandler.sendMessage(Message.obtain(mHandler, 1, twoLabelList));
    }

    public void setThreeLabelData(List<MAreaBean> threeLabelList) {
        mAreaAdapter = new MAreaAdapter(context, threeLabelList);
        mHandler.sendMessage(Message.obtain(mHandler, 2, threeLabelList));
    }
}