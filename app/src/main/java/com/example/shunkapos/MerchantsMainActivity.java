package com.example.shunkapos;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.merchantfragment.MerchantHomeFragment;
import com.example.shunkapos.merchantfragment.MerchantMeFragment;
import com.example.shunkapos.utils.StatusBarUtil;

/**
 * 作者: qgl
 * 创建日期：2022/6/22
 * 描述:商户版App
 */
public class MerchantsMainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottom_nav;
    //fragment数组
    private Fragment[] fragments;
    //初始显示的页码
    private int lastFragmentIndex = 0;
    //退出软件，开始时间
    private long mPressedTime = 0;
    @Override
    protected int getLayoutId() {
        StatusBarUtil.transparencyBar(MerchantsMainActivity.this);
        return R.layout.merchants_main_activity;
    }

    @Override
    protected void initView() {
        bottom_nav = findViewById(R.id.bottom_nav);
        //当你的图标是多色系时
        bottom_nav.setItemIconTintList(null);
        fragments = new Fragment[]{
                new MerchantHomeFragment(),
                new MerchantMeFragment()};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame, fragments[0])
                .commit();
    }

    @Override
    protected void initListener() {
        bottom_nav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //选中效果
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.home_btn:
                switchFragment(0);
                break;

            case R.id.me_btn:
                switchFragment(1);
                break;
        }
        return false;
    }

    private void switchFragment(int to) {
        if (lastFragmentIndex == to) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragments[to].isAdded()) {
            transaction.add(R.id.main_frame, fragments[to]);
        } else {
            transaction.show(fragments[to]);
        }
        transaction.hide(fragments[lastFragmentIndex]).commitAllowingStateLoss();
        lastFragmentIndex = to;
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
        //获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {
            //比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {
            //退出程序
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}