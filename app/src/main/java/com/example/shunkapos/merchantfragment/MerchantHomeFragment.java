package com.example.shunkapos.merchantfragment;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseFragment;
import com.example.shunkapos.demo.MerchantMineChantsActivity;
import com.example.shunkapos.demo.NewDemoActivity;
import com.example.shunkapos.demo.SetamountActivity;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.RealNameOnActivity;
import com.example.shunkapos.merchanthomeactivity.CollectionListActivity;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.views.MyDialog1;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2022/6/22
 * 描述:商户首页Fragment
 */
public class MerchantHomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView main_home_invite_partners;
    private TextView face_pay_btn;
    private String sMid = "";
    private String aliasName = "";
    private RelativeLayout collection_relative;

    private boolean isSMid = false;
    private TextView today_earnings_tv;
    private TextView money_tv;

    @Override
    protected int getLayoutInflaterResId() {
        return R.layout.merchant_home_fragment;
    }


    @Override
    protected void initView(View rootView) {
        main_home_invite_partners = rootView.findViewById(R.id.main_home_invite_partners);
        face_pay_btn = rootView.findViewById(R.id.face_pay_btn);
        collection_relative = rootView.findViewById(R.id.collection_relative);
        today_earnings_tv = rootView.findViewById(R.id.today_earnings_tv);
        money_tv = rootView.findViewById(R.id.money_tv);
        main_home_invite_partners.setOnClickListener(this);
        face_pay_btn.setOnClickListener(this);
        collection_relative.setOnClickListener(this);
        posData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_home_invite_partners:
                Intent intent = new Intent(getActivity(), NewDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.face_pay_btn:
                if (!isSMid){
                    showDialog();
                }else {
                    Intent intent1 = new Intent(getActivity(), SetamountActivity.class);
                    intent1.putExtra("smid", sMid);
                    intent1.putExtra("aliasName", aliasName);
                    startActivity(intent1);
                }
                break;
            case R.id.collection_relative:
                if (!isSMid){
                    showDialog();
                }else {
                    Intent intent2 = new Intent(getActivity(), CollectionListActivity.class);
                    intent2.putExtra("smid", sMid);
                    startActivity(intent2);
                }
                break;

        }
    }

    /**
     * 查询是否报件
     */
    private void posData() {
        RequestParams params = new RequestParams();
        params.put("merchantUserId", getMerchantUserId());
        HttpRequest.PosSmid(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    if (object.getString("data").equals("false")) {
                        showToast("您还未开通报件");
                        isSMid = false;
                        showDialog();
                    } else {
                        isSMid = true;
                        sMid = object.getJSONObject("data").getString("smid");
                        aliasName = object.getJSONObject("data").getString("aliasName");
                        getStatisticsInfo(sMid);
                    }
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

    /**
     * 提示用户去完善信息，实名认证
     */
    public void showDialog() {
        //dialog xml
        View view = LayoutInflater.from(mContext).inflate(R.layout.smid_dialog_layout, null);
        //初始化控件
        Button perfect_into = view.findViewById(R.id.perfect_into);
        ImageView perfect_out = view.findViewById(R.id.perfect_out);
        //设置dialog界面宽度
        Dialog dialog = new MyDialog1(getActivity(), true, true, (float) 0.7).setNewView(view);
        //设置点击dialog外围是否消失
        dialog.setCanceledOnTouchOutside(false);
        //显示
        dialog.show();
        //点击取消
        perfect_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.dismiss();
            }
        });
        //点击确认
        perfect_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), NewDemoActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 请求首页数据
     * @param id
     */
    private void getStatisticsInfo(String id){
        RequestParams params = new RequestParams();
        params.put("smid", id);
        HttpRequest.getStatisticsInfo(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    JSONObject data = object.getJSONObject("data");
                    today_earnings_tv.setText("今日收款"+data.getString("number")+"笔");
                    money_tv.setText(data.getString("totalAmount"));
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
}