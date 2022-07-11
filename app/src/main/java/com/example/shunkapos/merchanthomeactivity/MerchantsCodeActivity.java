package com.example.shunkapos.merchanthomeactivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.cap.encode.CodeCreator;
import com.example.shunkapos.utils.SPUtils;

/**
 * 作者: qgl
 * 创建日期：2022/7/9
 * 描述:我的商家码
 */
public class MerchantsCodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView merchants_code_iv;
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.merchantscode_activity;
    }

    @Override
    protected void initView() {
        merchants_code_iv = findViewById(R.id.merchants_code_iv);
        iv_back = findViewById(R.id.iv_back);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        merchants_code_iv.setImageBitmap(CodeCreator.createQRCode(SPUtils.get(this, "payUrl", "").toString(), 400, 400, null));
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}