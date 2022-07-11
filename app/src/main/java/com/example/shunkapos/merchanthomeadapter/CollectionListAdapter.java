package com.example.shunkapos.merchanthomeadapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shunkapos.R;
import com.example.shunkapos.merchanthomebean.CollectionListBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/7/8
 * 描述:收款记录
 */
public class CollectionListAdapter extends BaseQuickAdapter<CollectionListBean, BaseViewHolder> {

    public CollectionListAdapter(int layoutResId, @Nullable List<CollectionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionListBean item) {
        helper.setText(R.id.trans_name,item.getTradeNo());
        helper.setText(R.id.trans_time,item.getCreateTime());
        helper.setText(R.id.trans_price,item.getTotalAmount());
        helper.setText(R.id.trans_mrc_name,item.getSubject());
        String payCode = "";
        if (item.getIsRefund() != null &&!item.getIsRefund().equals("null")&&!item.getIsRefund().equals("")){
            if (item.getIsRefund().equals("1")){
                payCode = "退款成功";
            }else if (item.getIsRefund().equals("2")){
                payCode = "退款失败";
            }else if (item.getIsRefund().equals("3")){
                payCode = "退款失败";
            }
        }else {

            switch (item.getPayCode()){
                case "10000":
                    payCode = "支付成功";
                    break;
                case "40004":
                    payCode = "支付失败";
                    break;
                case "10003":
                    payCode = "等待用户付款";
                    break;
                case "20000":
                    payCode = "未知异常";
                    break;
                case "0":
                    payCode = "订单预创建";
                    break;
                case "1":
                    payCode = "交易完成";
                    break;
                case "2":
                    payCode = "未付款交易超时关闭";
                    break;
            }

        }
        helper.setText(R.id.collapse_type,payCode);

    }
}