package com.example.shunkapos.demo.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shunkapos.R;
import com.example.shunkapos.demo.demobean.MerchantMineChantsBean;
import com.example.shunkapos.homefragment.homemerchants.memerchants.adapter.MeMerchantsAdapter;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/23
 * 描述:商户版 我的商户
 */
public class MerchantMineChantsAdapter extends BaseQuickAdapter<MerchantMineChantsBean, BaseViewHolder> {
    //回调方法
    private ChecksTandCallback callback;

    public MerchantMineChantsAdapter(int layoutResId, @Nullable List<MerchantMineChantsBean> data,ChecksTandCallback callback) {
        super(layoutResId, data);
        this.callback = callback;
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantMineChantsBean item) {
            helper.setText(R.id.me_merchants_name,item.getName());
            helper.setText(R.id.me_merchants_number,item.getExternalId());
            String sTatus = "";
            String subConfirm = "";
            if (!TextUtils.isEmpty(item.getStatus())){
                if (item.getStatus().equals("99")){
                    sTatus = "审核状态：报件成功";
                }else if (item.getStatus().equals("-1")){
                    sTatus = "审核状态：报件失败";
                }else if (item.getStatus().equals("031")){
                    sTatus = "审核状态：审核中";
                }
            }
            if (!TextUtils.isEmpty(item.getSubConfirm())){
                if (item.getSubConfirm().equals("FAIL")){
                subConfirm = "签约失败";
                }else if (item.getSubConfirm().equals("FINISH")){
                    subConfirm = "生成二维码";
                }else if (item.getSubConfirm().equals("CREATE")){
                    subConfirm = "已发起签约";

                }else if (item.getSubConfirm().equals("NOT_CONFIRM")){
                    subConfirm = "未确认签约";
                }
            }
            helper.setText(R.id.signing_btn,subConfirm);
            helper.setText(R.id.status,sTatus);

            helper.setOnClickListener(R.id.signing_btn, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.addAction(item.getSmid(),item.getName());
                }
            });

    }

    public interface ChecksTandCallback {
        void addAction(String id,String name);
    }
}