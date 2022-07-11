package com.example.shunkapos.merchantfragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseFragment;
import com.example.shunkapos.cap.android.CaptureActivity;
import com.example.shunkapos.cap.bean.ZxingConfig;
import com.example.shunkapos.cap.common.Constant;
import com.example.shunkapos.demo.SetamountActivity;
import com.example.shunkapos.mefragment.meabout.MeAboutActivity;
import com.example.shunkapos.mefragment.setup.SetUpActivity;
import com.example.shunkapos.merchanthomeactivity.MerchantsCodeActivity;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.utils.SPUtils;
import com.example.shunkapos.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 作者: qgl
 * 创建日期：2022/6/22
 * 描述:商户我的Fragment
 */
public class MerchantMeFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout me_merchants_about;
    private RelativeLayout me_merchants_setup;
    private RelativeLayout Binding_merchants_rela;
    private RelativeLayout Binding_code_rela;
    private TextView recommended_person_tv;
    private TextView main_me_name;
    private TextView main_me_cooperation_code;
    //绑定商户状态  1绑定 -1 未绑定
    private String isBindingUser = "-1";
    private View view_line1;
    private View view_line2;
    @Override
    protected int getLayoutInflaterResId() {
        return R.layout.merchants_me_fragment;
    }

    @Override
    protected void initView(View rootView) {
        me_merchants_about = rootView.findViewById(R.id.me_merchants_about);
        me_merchants_setup = rootView.findViewById(R.id.me_merchants_setup);
        Binding_merchants_rela = rootView.findViewById(R.id.Binding_merchants_rela);
        Binding_code_rela = rootView.findViewById(R.id.Binding_code_rela);
        recommended_person_tv = rootView.findViewById(R.id.recommended_person_tv);
        main_me_name = rootView.findViewById(R.id.main_me_name);
        main_me_cooperation_code = rootView.findViewById(R.id.main_me_cooperation_code);
        view_line1 = rootView.findViewById(R.id.view_line1);
        view_line2 = rootView.findViewById(R.id.view_line2);
        me_merchants_about.setOnClickListener(this);
        me_merchants_setup.setOnClickListener(this);
        Binding_merchants_rela.setOnClickListener(this);
        Binding_code_rela.setOnClickListener(this);
        main_me_name.setText(SPUtils.get(getActivity(), "merchantName", "").toString());
        main_me_cooperation_code.setText(SPUtils.get(getActivity(), "mUsername", "").toString());
        recommended_person_tv.setText(SPUtils.get(getActivity(), "nickName", "").toString());
        if (!TextUtils.isEmpty(SPUtils.get(getActivity(), "isBindingUser", "").toString())){
            isBindingUser = SPUtils.get(getActivity(), "isBindingUser", "").toString();
        }
        if (isBindingUser.equals("1")){
            view_line1.setVisibility(View.GONE);
            Binding_merchants_rela.setVisibility(View.GONE);
            view_line2.setVisibility(View.VISIBLE);
            Binding_code_rela.setVisibility(View.VISIBLE);
        }else {
            view_line1.setVisibility(View.VISIBLE);
            Binding_merchants_rela.setVisibility(View.VISIBLE);
            view_line2.setVisibility(View.GONE);
            Binding_code_rela.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.me_merchants_about:
                startActivity(new Intent(getActivity(), MeAboutActivity.class));
                break;
            case R.id.me_merchants_setup:
                startActivity(new Intent(getActivity(), SetUpActivity.class));
                break;
            case R.id.Binding_merchants_rela:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setShowbottomLayout(false);//底部布局（包括闪光灯和相册）
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                config.setFullScreenScan(true);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                intent.putExtra("wid",0.6);
                intent.putExtra("hei",0.7);
                startActivityForResult(intent, 2);
                break;
            case R.id.Binding_code_rela:
                startActivity(new Intent(getActivity(), MerchantsCodeActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == 2) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Map urlList = Utility.getUrlList(content);
                String id = (String) urlList.get("id");
                bindMerch(id);
            }
        }
    }

    /**
     *
     * @param content
     */
    private void bindMerch(String content) {
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("userId", getmUserId());
        params.put("merchantUserId", getMerchantUserId());
        params.put("id", content);
        HttpRequest.getisBindingUser(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    showToast(object.getString("msg"));
                    view_line1.setVisibility(View.GONE);
                    Binding_merchants_rela.setVisibility(View.GONE);

                    view_line2.setVisibility(View.VISIBLE);
                    Binding_code_rela.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Failuer(failuer.getEcode(), failuer.getEmsg());

            }
        });
    }



}