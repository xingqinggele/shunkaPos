package com.example.shunkapos.merchanthomeactivity;

import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2022/7/8
 * 描述:收款状态
 */
public class CollectionStaticActivity extends BaseActivity {
    private TextView order_num_tv;
    private TextView zfb_order_num_tv;
    private TextView order_price_tv;
    private TextView order_time_tv;
    private TextView order_static_tv;
    private TextView order_zfb_num_tv;
    @Override
    protected int getLayoutId() {
        return R.layout.collection_static_activity;
    }

    @Override
    protected void initView() {
        order_num_tv = findViewById(R.id.order_num_tv);
        zfb_order_num_tv = findViewById(R.id.zfb_order_num_tv);
        order_price_tv = findViewById(R.id.order_price_tv);
        order_time_tv = findViewById(R.id.order_time_tv);
        order_static_tv = findViewById(R.id.order_static_tv);
        order_zfb_num_tv = findViewById(R.id.order_zfb_num_tv);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        order_num_tv.setText(getIntent().getStringExtra("outTradeNo"));
        zfb_order_num_tv.setText(getIntent().getStringExtra("tradeNo"));
        order_price_tv.setText(getIntent().getStringExtra("buyerPayAmount"));
        order_time_tv.setText(getIntent().getStringExtra("sendPayDate"));
        order_zfb_num_tv.setText(getIntent().getStringExtra("buyerUserId"));
        order_static_tv.setText("支付成功");
    }
}