package com.example.shunkapos.demo;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shunkapos.R;
import com.example.shunkapos.homefragment.homeInvitepartners.HomeNewFilBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2022/6/9
 * 描述:
 */
public class DemoAdapter extends BaseQuickAdapter<HomeNewFilBean, BaseViewHolder> {

    private onEditClink editClink;

    public DemoAdapter(int layoutResId, @Nullable List<HomeNewFilBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeNewFilBean item) {
        EditText serverEdit = helper.itemView.findViewById(R.id.serverEdit);
        helper.setText(R.id.serverName,item.getServerName());
        helper.setText(R.id.serverNum,"(0~"+item.getServerMoney()+")");
        serverEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editClink.onEdit(charSequence.toString(),item.getId());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public interface onEditClink{
        void onEdit(String value, int position);

    }

    public void setEditClink(onEditClink editClink){
        this.editClink = editClink;
    }


}