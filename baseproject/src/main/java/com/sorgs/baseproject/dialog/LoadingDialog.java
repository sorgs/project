package com.sorgs.baseproject.dialog;

import android.content.Context;

import com.sorgs.baseproject.R;
import com.sorgs.baseproject.action.AnimAction;
import com.sorgs.baseproject.base.BaseDialog;


/**
 * author : Android 轮子哥
 * time   : 2020/4/29
 * desc   : 等待加载对话框(动画)
 */
public final class LoadingDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_loading);
            setAnimStyle(AnimAction.TOAST);
            setBackgroundDimEnabled(false);
            setCancelable(false);
        }
    }
}