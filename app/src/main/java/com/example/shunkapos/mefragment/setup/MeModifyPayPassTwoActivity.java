package com.example.shunkapos.mefragment.setup;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import static com.example.shunkapos.utils.Utility.equalStr;
import static com.example.shunkapos.utils.Utility.isContinuousNum;

/**
 * 作者: qgl
 * 创建日期：2021/2/5
 * 描述: 修改支付密码2
 */
public class MeModifyPayPassTwoActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;  // 返回键
    private EditText me_pay_pass_two_word_ed;  // 新密码
    private EditText me_pay_pass_two_word_ed1; // 再次输入密码
    private Button me_pay_pass_two_bt;  // 提交按钮

    @Override
    protected int getLayoutId() {
        //设置状态栏颜色
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.memodifypaypasstwo_activity;
    }

    // 初始化控件
    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        me_pay_pass_two_bt = findViewById(R.id.me_pay_pass_two_bt);
        me_pay_pass_two_word_ed = findViewById(R.id.me_pay_pass_two_word_ed);
        me_pay_pass_two_word_ed1 = findViewById(R.id.me_pay_pass_two_word_ed1);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        me_pay_pass_two_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.me_pay_pass_two_bt:
                if (me_pay_pass_two_word_ed.getText().toString().trim().length() < 6) {
                    showToast(3, "密码长度必须6位");
                    return;
                }
                String str = me_pay_pass_two_word_ed.getText().toString().trim();
                if (equalStr(str)) {
                    showToast(3, "密码不能重复数字");
                    return;
                }
                if (isContinuousNum(str)) {
                    showToast(3, "密码不能连续数字");
                    return;
                }
                if (!TextUtils.equals(me_pay_pass_two_word_ed.getText().toString().trim(), me_pay_pass_two_word_ed1.getText().toString().trim())) {
                    showToast(3, "两次输入密码不一致，请详细检查");
                    return;
                }
                //提交修改支付密码
                posData();
                break;
        }
    }


    // 提交支付密码
    public void posData() {
        RequestParams params = new RequestParams();
        params.put("password", me_pay_pass_two_word_ed.getText().toString().trim());
        HttpRequest.getPay_password2(params, getToken(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                hideInput();
                showToast(3, "支付密码修改成功");
                finish();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(),failuer.getEmsg());

            }
        });

    }

}
