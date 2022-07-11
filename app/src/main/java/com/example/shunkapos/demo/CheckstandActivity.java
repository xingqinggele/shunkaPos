package com.example.shunkapos.demo;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2022/6/24
 * 描述:收银台
 */
public class CheckstandActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private TextView face_pay_btn;
    private TextView company_name;
    private String smid;
    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color,false).init();
        return R.layout.checkstand_activity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        company_name = findViewById(R.id.company_name);
        face_pay_btn = findViewById(R.id.face_pay_btn);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        face_pay_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        company_name.setText(getIntent().getStringExtra("name"));
        smid = getIntent().getStringExtra("smid");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.face_pay_btn:
                Intent intent = new Intent(this, SetamountActivity.class);
                intent.putExtra("smid",smid);
                startActivity(intent);
                break;
        }
    }
}