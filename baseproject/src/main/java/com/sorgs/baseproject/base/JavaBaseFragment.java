package com.sorgs.baseproject.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorgs.baseproject.R;
import com.sorgs.baseproject.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: Fragment基类,带ButterKnife.
 *
 * @author Sorgs.
 * Created date: 2019/4/14.
 */
public abstract class JavaBaseFragment extends Fragment {
    /**
     * Tag
     */
    protected String TAG = getClass().getSimpleName();
    /**
     * 上下文
     */
    protected Context mContext;
    protected Activity mActivity;
    protected View mEmptyView;
    /**
     * 展示错误布局，是否可以点击重试
     */
    protected boolean mCanRetry;
    private Unbinder mUnBinder;
    private View mProgressView;
    private View mLoadingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initLayoutId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
    }

    protected void initView() {
        if (mProgressView == null) {
            ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int initLayoutId();

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
