package com.example.shunkapos.demo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.cap.android.CaptureActivity;
import com.example.shunkapos.cap.bean.ZxingConfig;
import com.example.shunkapos.cap.common.Constant;
import com.example.shunkapos.homefragment.homeequipment.activity.TransferPersonActivity;
import com.example.shunkapos.merchanthomeactivity.CollectionStaticActivity;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.utils.DecimalInputTextWatcher;
import com.example.shunkapos.views.MyDialog1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 作者: qgl
 * 创建日期：2022/6/24
 * 描述:设置金额
 */
public class SetamountActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private LinearLayout iv_back;
    private String smid = "";
    //支付宝支付
    private CheckBox alipay_check;
    //花呗分期A
    private CheckBox spend_bai_check;
    //花呗分期B
    private CheckBox spend_bai_Bcheck;
    //设置金额
    private EditText money_edit;
    // 扫码
    private RadioButton scan_btn;
    //二维码
    private RadioButton code_btn;
    //花呗A选项
    private RadioGroup hua_radio_group;
    //花呗b选项
    private RadioGroup hua_radio_Bgroup;
    // 1 支付宝 2 花呗A 2 花呗B
    private String pay_type = "1";
    //花呗 选项 6、12
    private String huaA_type = "";
    //支付描述
    private String aliasName = "";

    private String meType = "1";


    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.setamount_activity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        alipay_check = findViewById(R.id.alipay_check);
        spend_bai_check = findViewById(R.id.spend_bai_check);
        spend_bai_Bcheck = findViewById(R.id.spend_bai_Bcheck);
        money_edit = findViewById(R.id.money_edit);
        scan_btn = findViewById(R.id.scan_btn);
        code_btn = findViewById(R.id.code_btn);
        hua_radio_group = findViewById(R.id.hua_radio_group);
        hua_radio_Bgroup = findViewById(R.id.hua_radio_Bgroup);
        money_edit.addTextChangedListener(new DecimalInputTextWatcher(money_edit, 2, 15));
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        alipay_check.setOnCheckedChangeListener(this);
        spend_bai_check.setOnCheckedChangeListener(this);
        spend_bai_Bcheck.setOnCheckedChangeListener(this);
        scan_btn.setOnClickListener(this);
        code_btn.setOnClickListener(this);
        hua_radio_group.setOnCheckedChangeListener(this);
        hua_radio_Bgroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        smid = getIntent().getStringExtra("smid");
        aliasName = getIntent().getStringExtra("aliasName");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.scan_btn:
                if (TextUtils.isEmpty(money_edit.getText().toString().trim())) {
                    showToast(3, "请输入金额");
                    return;
                }
                if (!spend_bai_Bcheck.isChecked()) {
                    showDialog(0, "扫一扫收款");
                } else {
                    showToast(3, "网站支付不能扫一扫");
                }

                break;
            case R.id.code_btn:
                if (TextUtils.isEmpty(money_edit.getText().toString().trim())) {
                    showToast(3, "请输入金额");
                    return;
                }
                if (alipay_check.isChecked()) {
                    pay_type = "1";
                    huaA_type = "";
                    meType = "1";
                }
                if (spend_bai_check.isChecked()) {
                    pay_type = "2";
                    meType = "2";
                }
                if (spend_bai_Bcheck.isChecked()) {
                    pay_type = "2";
                    meType = "3";
                }
                showDialog(1, "二维码收款");
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.alipay_check:
                if (alipay_check.isChecked()) {
                    alipay_check.setChecked(true);
                    spend_bai_check.setChecked(false);
                    spend_bai_Bcheck.setChecked(false);
                } else {
                    alipay_check.setChecked(false);
                }
                break;
            case R.id.spend_bai_check:
                if (spend_bai_check.isChecked()) {
                    spend_bai_check.setChecked(true);
                    spend_bai_Bcheck.setChecked(false);
                    alipay_check.setChecked(false);
                    hua_radio_group.check(R.id.radio_btn_six);
                } else {
                    spend_bai_check.setChecked(false);
                    hua_radio_group.clearCheck();
                }
                break;
            case R.id.spend_bai_Bcheck:
                if (spend_bai_Bcheck.isChecked()) {
                    hua_radio_Bgroup.check(R.id.radio_btn_Bsix);
                    spend_bai_Bcheck.setChecked(true);
                    alipay_check.setChecked(false);
                    spend_bai_check.setChecked(false);
                } else {
                    hua_radio_Bgroup.clearCheck();
                    spend_bai_Bcheck.setChecked(false);
                }
                break;
        }
    }


    private void showDialog(int type, String title) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_setamount_item, null);
        TextView textView = view.findViewById(R.id.dialog_tv1);
        TextView dialog_cancel = view.findViewById(R.id.dialog_cancel);
        TextView dialog_determine = view.findViewById(R.id.dialog_determine);
        Dialog dialog = new MyDialog1(this, true, true, (float) 0.7).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        textView.setText(title);
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
                if (type == 0) {


                    Intent intent = new Intent(SetamountActivity.this, CaptureActivity.class);
                    /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                     * 也可以不传这个参数
                     * 不传的话  默认都为默认不震动  其他都为true
                     * */
                    ZxingConfig config = new ZxingConfig();
                    config.setShowbottomLayout(false);//底部布局（包括闪光灯和相册）
                    config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                    config.setFullScreenScan(true);
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                    intent.putExtra("wid", 0.6);
                    intent.putExtra("hei", 0.7);
                    startActivityForResult(intent, 2);
                } else {
                    Intent intent = new Intent(SetamountActivity.this, QrcodeActivity.class);
                    intent.putExtra("smid", smid);
                    intent.putExtra("totalAmount", money_edit.getText().toString().trim());
                    intent.putExtra("subject", aliasName);
                    intent.putExtra("type", pay_type);
                    intent.putExtra("hbFqNum", huaA_type);
                    intent.putExtra("meType", meType);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_btn_six:
                if (spend_bai_check.isChecked()) {
                    huaA_type = "6";
                } else {
                    hua_radio_group.clearCheck();
                }
                break;

            case R.id.radio_btn_twelve:
                if (spend_bai_check.isChecked()) {
                    huaA_type = "12";
                } else {
                    hua_radio_group.clearCheck();
                }
                break;

            case R.id.radio_btn_Bsix:
                if (spend_bai_Bcheck.isChecked()) {
                    huaA_type = "6";
                } else {
                    hua_radio_Bgroup.clearCheck();
                }
                break;

            case R.id.radio_btn_Btwelve:
                if (spend_bai_Bcheck.isChecked()) {
                    huaA_type = "12";
                } else {
                    hua_radio_Bgroup.clearCheck();
                }
                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null){
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                shouLog("----->",content);
                posScanCode(content);
            }
        }
    }


    private void posScanCode(String authCode) {
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("totalAmount", money_edit.getText().toString().trim());
        params.put("authCode", authCode);
        params.put("subject", aliasName);
        params.put("smid", smid);
        params.put("type", pay_type);
        params.put("payType", "1");
        params.put("userId", getmUserId());
        if (!huaA_type.equals("")) {
            params.put("hbFqNum", huaA_type);
        }
        HttpRequest.PostpaymentCode(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                Intent intent = new Intent(SetamountActivity.this, CollectionStaticActivity.class);
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    JSONObject data = object.getJSONObject("data");
                        intent.putExtra("buyerLogonId",data.getString("buyerLogonId"));
                        intent.putExtra("buyerUserId",data.getString("buyerUserId"));
                        intent.putExtra("buyerPayAmount",data.getString("buyerPayAmount"));
                        intent.putExtra("outTradeNo",data.getString("outTradeNo"));
                        intent.putExtra("sendPayDate",data.getString("sendPayDate"));
                        intent.putExtra("tradeNo",data.getString("tradeNo"));
                        intent.putExtra("tradeStatus",data.getString("tradeStatus"));
                        intent.putExtra("errorCode",data.getString("errorCode"));
                        startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Failuer(failuer.getEcode(), failuer.getMessage());
            }
        });

    }
}