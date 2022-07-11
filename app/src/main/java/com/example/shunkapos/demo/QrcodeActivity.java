package com.example.shunkapos.demo;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.cap.encode.CodeCreator;
import com.example.shunkapos.merchanthomeactivity.CollectionStaticActivity;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2022/6/24
 * 描述:支付宝生成二维码
 */
public class QrcodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView src_code;
    private String totalAmount = "";
    private String subject = "";
    private String type = "";
    private String hbFqNum = "";
    private String smid = "";
    private LinearLayout iv_back;
    private String outTradeNo = "";
    private String meType = "";
    private String zPay = "http://shunka168.com/zfb/index.html?id=";
    private String wPay = "http://shunka168.com/zfb/wappay.html?id=";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            posQueryOrder(outTradeNo);
            handler.postDelayed(this, 2000);
        }
    };


    @Override
    protected int getLayoutId() {
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.qrcode_activity;
    }

    @Override
    protected void initView() {
        src_code = findViewById(R.id.src_code);
        iv_back = findViewById(R.id.iv_back);

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        smid = getIntent().getStringExtra("smid");
        totalAmount = getIntent().getStringExtra("totalAmount");
        subject = getIntent().getStringExtra("subject");
        hbFqNum = getIntent().getStringExtra("hbFqNum");
        meType = getIntent().getStringExtra("meType");
        posData();

    }

    private void posData() {
        RequestParams params = new RequestParams();
        params.put("totalAmount", totalAmount);
        params.put("subject", subject);
        params.put("smid", smid);
        params.put("type", type);
        if (meType.equals("3")){
            params.put("payType", "2");
        }else {
            params.put("payType", "1");
        }
        params.put("userId ", getmUserId());
        if (!hbFqNum.equals("")) {
            params.put("hbFqNum", hbFqNum);
        }

        HttpRequest.PosCreateTradeWapPay(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    JSONObject data = object.getJSONObject("data");
                    if (meType.equals("3")){
                        src_code.setImageBitmap(CodeCreator.createQRCode(wPay + data.getString("id"), 400, 400, null));
                    }else {
                        src_code.setImageBitmap(CodeCreator.createQRCode(zPay + data.getString("id"), 400, 400, null));
                    }
                    outTradeNo = data.getString("outTradeNo");
                    posQueryOrder(outTradeNo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(), failuer.getMessage());
            }
        });
    }

    private void posQueryOrder(String outTradeNo) {
        RequestParams params = new RequestParams();
        params.put("outTradeNo", outTradeNo);
        HttpRequest.PosQuery(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    Intent intent = new Intent(QrcodeActivity.this, CollectionStaticActivity.class);
                    JSONObject object = new JSONObject(responseObj.toString());
                    JSONObject data = object.getJSONObject("data");
                    if (data.getString("tradeStatus").equals("null")) {

                    } else {
                        //交易创建，等待买家付款
                        if (data.getString("tradeStatus").equals("WAIT_BUYER_PAY")) {
                            //延时操作
                            if (handler != null) {
                                handler.removeCallbacks(runnable);
                            }
                            handler.postDelayed(runnable, 10000);
                            //交易支付成功
                        } else if (data.getString("tradeStatus").equals("TRADE_SUCCESS")) {
                            if (handler != null) {
                                handler.removeCallbacks(runnable);
                            }
                            intent.putExtra("buyerLogonId",data.getString("buyerLogonId"));
                            intent.putExtra("buyerUserId",data.getString("buyerUserId"));
                            intent.putExtra("buyerPayAmount",data.getString("buyerPayAmount"));
                            intent.putExtra("outTradeNo",data.getString("outTradeNo"));
                            intent.putExtra("sendPayDate",data.getString("sendPayDate"));
                            intent.putExtra("tradeNo",data.getString("tradeNo"));
                            intent.putExtra("tradeStatus",data.getString("tradeStatus"));
                            intent.putExtra("errorCode",data.getString("errorCode"));
                            startActivity(intent);
                            //未付款交易超时关闭，或支付完成后全额退款
                        } else if (data.getString("tradeStatus").equals("TRADE_CLOSED")) {
                            if (handler != null) {
                                handler.removeCallbacks(runnable);
                            }
                            //交易结束，不可退款
                        } else if (data.getString("tradeStatus").equals("TRADE_FINISHED")) {
                            if (handler != null) {
                                handler.removeCallbacks(runnable);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //Failuer(failuer.getEcode(),failuer.getMessage());
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
                handler.postDelayed(runnable, 10000);
            }
        });

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
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}