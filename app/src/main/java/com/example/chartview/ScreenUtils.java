package com.example.chartview;

import android.content.Context;

/**
 * create by libo
 * create on 2020/7/26
 * description
 */
public class ScreenUtils {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
