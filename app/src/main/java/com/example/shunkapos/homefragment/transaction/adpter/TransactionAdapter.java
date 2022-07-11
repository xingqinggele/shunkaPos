package com.example.shunkapos.homefragment.transaction.adpter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shunkapos.R;
import com.example.shunkapos.homefragment.transaction.bean.TransactionBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2021/10/29
 * 描述:交易记录Adapter
 */
public class TransactionAdapter extends BaseQuickAdapter<TransactionBean, BaseViewHolder> {

    public TransactionAdapter(int layoutResId, @Nullable List<TransactionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionBean item) {
        helper.setText(R.id.trans_name, "姓名：" + item.getAcqName());
        helper.setText(R.id.trans_time, item.getHostDate());
        helper.setText(R.id.trans_price, "+" + item.getAmnount());
    }

}