package com.example.shunkapos.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 作者: qgl
 * 创建日期：2020/12/25
 * 描述:dp和px互转的工具类
 */
public class ScaleUtils {
    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //px转dp
    public static int px2dip(Context context, int pxValue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics()));
    }

}
