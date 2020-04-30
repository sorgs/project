package com.sorgs.baseproject.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.sorgs.baseproject.action.BundleAction;
import com.sorgs.baseproject.action.ClickAction;
import com.sorgs.baseproject.action.ContextAction;
import com.sorgs.baseproject.action.HandlerAction;
import com.sorgs.baseproject.action.TitleBarAction;

import java.util.Random;

/**
 * @author sorgs
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : Fragment 基类
 */
public abstract class BaseFragment<A extends BaseActivity> extends Fragment
        implements ContextAction, HandlerAction, ClickAction, BundleAction,
        TitleBarAction {

    protected final String TAG = getClass().getSimpleName();

    public Context mContext;

    /**
     * Activity 对象
     */
    protected A mActivity;

    /**
     * 根布局
     */
    private View mRootView;

    /**
     * 是否初始化过
     */
    private boolean mInitialize;

    /**
     * startActivityForResult 方法优化
     */
    private BaseActivity.OnActivityCallback mActivityCallback;

    private int mActivityRequestCode;

    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;

    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;


    /**
     * 根据资源 id 获取一个 View 对象
     */
    @Override
    public <V extends View> V findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    public void startActivityForResult(Class<? extends Activity> clazz, BaseActivity.OnActivityCallback callback) {
        startActivityForResult(new Intent(mActivity, clazz), null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, BaseActivity.OnActivityCallback callback) {
        // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        }
    }

    public void startActivityForResult(Intent intent, BaseActivity.OnActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获得全局的 Activity
        mActivity = (A) requireActivity();
        mContext = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @NonNull
    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mInitialize) {
            mInitialize = true;
            initFragment();
        }
        // 重新初始化状态栏
        statusBarConfig().init();
    }

    @Override
    public void onDetach() {
        removeCallbacks();
        mActivity = null;
        mContext = null;
        super.onDetach();
    }

    /**
     * 获取布局 ID
     */
    protected abstract int getLayoutId();

    protected void initFragment() {
        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }
        initImmersion();
        initView();
        initData();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig().init();

            // 设置标题栏沉浸
            if (mTitleBar != null) {
                ImmersionBar.setTitleBar(this, mTitleBar);
            }
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                                    // 默认状态栏字体颜色为黑色
                                    .statusBarDarkFont(statusBarDarkFont())
                                    // 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                                    .keyboardEnable(true);
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        // 返回真表示黑色字体
        return true;
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = findTitleBar((ViewGroup) getView());
        }
        return mTitleBar;
    }

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowLoadingDialog() {
        return mActivity.isShowLoadingDialog();
    }

    /**
     * 显示加载对话框
     */
    public void showLoadingDialog() {
        mActivity.showLodingDialog();
    }

    /**
     * 隐藏加载对话框
     */
    public void hideLoadingDialog() {
        mActivity.hideLoadingDialog();
    }

    /**
     * 当前加载动画是否在显示中
     */
    public boolean isShowLoadingView() {
        return mActivity.isShowLoadingView();
    }

    /**
     * 显示加载动画
     */
    public void showLoadingView() {
        mActivity.showLodingView();
    }

    /**
     * 隐藏加载动画
     */
    public void hideLoadingView() {
        mActivity.hideLoadingView();
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        if (mActivity != null && !mActivity.isFinishing()) {
            mActivity.finish();
        }
    }

    /**
     * Fragment 返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件，回传给 Activity
        return false;
    }
}