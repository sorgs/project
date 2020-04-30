package com.sorgs.baseproject.action;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author sorgs
 * time   : 2019/09/15
 * desc   : 点击事件意图
 */
public interface ClickAction extends View.OnClickListener {

    @Override
    default void onClick(View v) {
        // 默认不实现，让子类实现
    }

    default void setOnClickListener(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    <V extends View> V findViewById(@IdRes int id);
}