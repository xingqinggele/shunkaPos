package com.example.shunkapos.views;

import android.app.Dialog;
import android.content.Context;

/**
 * 作者: qgl
 * 创建日期：2020/12/25
 * 描述:
 */
public class BaseDialog extends Dialog {
    private int res;

    public BaseDialog(Context context, int theme, int res) {
        super(context, theme);
        // TODO 自动生成的构造函数存根
        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }
}
