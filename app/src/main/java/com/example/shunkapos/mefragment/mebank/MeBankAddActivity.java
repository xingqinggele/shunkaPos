package com.example.shunkapos.mefragment.mebank;

import android.view.View;
import android.widget.LinearLayout;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/12/22
 * 描述:新增银行卡
 */
public class MeBankAddActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.me_bank_add_activity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
