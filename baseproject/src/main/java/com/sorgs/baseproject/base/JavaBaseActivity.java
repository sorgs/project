package com.sorgs.baseproject.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.sorgs.baseproject.R;
import com.sorgs.baseproject.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: Activity基类，带ButterKnife.
 *
 * @author Sorgs.
 * Created date: 2019/4/14.
 */
public abstract class JavaBaseActivity extends AppCompatActivity {
    /**
     * Tag
     */
    protected String TAG = getClass().getSimpleName();
    /**
     * 上下文
     */
    protected Context mContext;
    protected View mEmptyView;
    /**
     * 展示错误布局，是否可以点击重试
     */
    protected boolean mCanRetry;
    private Unbinder mUnBinder;
    private View mProgressView;
    private View mLoadingView;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = initLayoutId();
        //如果initView返回0,则不会调用setContentView(),当然也不会 Bind ButterKnife
        if (layoutResID != 0) {
            setContentView(layoutResID);
            //绑定到butterKnife
            mUnBinder = ButterKnife.bind(this);
        }
        //修改标题栏颜色为暗色主题颜色（黑色）
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mContext = this;
        initView(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 添加布局文件
     *
     * @return 布局文件
     */
    protected abstract int initLayoutId();

    protected void initView(@Nullable Bundle savedInstanceState) {
        if (mProgressView == null) {
            ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
            mProgressView = getLayoutInflater().inflate(R.layout.loading_layout, rootView, false);
            mLoadingView = mProgressView.findViewById(R.id.loading_view);
            setProgressVisible(false);
            rootView.addView(mProgressView);
        }
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    /**
     * 是否展示loading
     *
     * @param show true展示
     */
    public void setProgressVisible(boolean show) {
        if (mProgressView != null) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }

    /**
     * 初始化空布局
     * 并非所有都需要
     */
    @SuppressLint("InflateParams")
    protected void initEmpty(int layoutId) {
        if (layoutId == 0) {
            LogUtils.INSTANCE.e(TAG, "layoutId is null");
            return;
        }
        mEmptyView = getLayoutInflater().inflate(layoutId, null);
    }

}
