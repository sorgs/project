package com.sorgs.baseproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.sorgs.baseproject.R;


/**
 * description: 耗时操作需要的弹窗，例如需要等待服务器返回后才能让用户继续操作的地方。
 */
public class LoadingDialog extends Dialog {
    private LoadingView mLoadingView;
    /**
     * 是否需要背景全透明
     */
    private boolean needTransBG;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.aimy_dialog_style);
    }

    public LoadingDialog(@NonNull Context context, boolean transBG) {
        super(context, R.style.aimy_dialog_style);
        needTransBG = transBG;
    }

    @Override
    public void show() {
        super.show();
        if (mLoadingView != null) {
            mLoadingView.startAnimation();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mLoadingView != null) {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                mLoadingView.stopAnimation();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        mLoadingView = findViewById(R.id.dialog_loading_view);
        if (needTransBG) {
            init();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.CENTER);
        // 设置宽度
        //        lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.7833f);
        //        lp.height = mContext.getResources().getDisplayMetrics().heightPixels - ScreenUtils.getStatusHeight
        //        (mContext);
        // TYPE_APPLICATION_ATTACHED_DIALOG类似于面板窗口，绘制类似于顶层窗口，而不是宿主的子窗口
        //			lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        // 整個Dialog的亮度
        // lp.screenBrightness = 0.2f;
        //Dialog内部的透明度
        //lp.alpha = 0.8f;
        // Dialog外部灰色的透明度
        lp.dimAmount = 0f;
        window.setAttributes(lp);
        // 添加动画
        window.setWindowAnimations(android.R.style.Animation_Dialog);
    }
}
