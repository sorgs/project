package com.sorgs.baseproject.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorgs.baseproject.R;
import com.sorgs.baseproject.utils.LogUtils;
import com.sorgs.baseproject.utils.NetworkUtils;
import com.sorgs.baseproject.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 自定义多状态的Layout
 */
public class MultiStatusLayout extends FrameLayout {

    private LayoutInflater mInflater;
    private int mEmptyResId = NO_ID, mLoadingResId = NO_ID, mErrorResId = NO_ID;
    private int mContentId = NO_ID;
    private int mStaticTopId = NO_ID;

    private View staticTopView;

    private Map<Integer, View> mLayouts = new HashMap<>();

    private int mEmptyImage = NO_ID;
    private CharSequence mEmptyText = null;

    private int mErrorImage = NO_ID;
    private CharSequence mErrorText = null;

    private OnRetryCallBack retryCallBack = null;

    private LoadingView loadingView;

    private int topPadding = 0;
    private int bottomPadding = 0;

    public MultiStatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public MultiStatusLayout(@NonNull Context context,
                             @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);

        mLoadingResId = R.layout.layout_loading_view;
        mEmptyResId = R.layout.layout_empty_no_data;
        mErrorResId = R.layout.layout_empty_net_error;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }

    public void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
        if (staticTopView == null && mContentId != NO_ID) {
            View contentView = layout(mContentId);
            staticTopView = contentView.findViewById(mStaticTopId);
            if (contentView instanceof ViewGroup) {
                try {
                    ((ViewGroup) contentView).removeView(staticTopView);
                } catch (Exception e) {
                    LogUtils.INSTANCE.e("e:" + e.getMessage());
                }
            }
            if (staticTopView != null) {
                staticTopView.bringToFront();
                addView(staticTopView);
            }
        }
    }

    public void showLoading() {
        if (layout(mLoadingResId).getVisibility() == VISIBLE) {
            return;
        }
        show(mLoadingResId);
    }

    /**
     * 根据布局获取View
     *
     * @param layoutId 布局资源文件id
     */
    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        if (mContentId != layoutId) {
            if (topPadding != 0) {
                layoutParams.topMargin = topPadding;
            }
            if (bottomPadding != 0) {
                layoutParams.bottomMargin = bottomPadding;
            }
        }
        addView(layout, layoutParams);
        mLayouts.put(layoutId, layout);

        if (layoutId == mEmptyResId) {
            ImageView img = layout.findViewById(R.id.empty_no_date_image);
            if (img != null && mEmptyImage != NO_ID) {
                img.setImageResource(mEmptyImage);
            }
            TextView view = layout.findViewById(R.id.empty_no_data_tv);
            if (view != null && mEmptyText != null) {
                view.setText(mEmptyText);
            }
        } else if (layoutId == mErrorResId) {
            ImageView img = layout.findViewById(R.id.empty_net_error_image);
            if (img != null && mErrorImage != NO_ID) {
                img.setImageResource(mErrorImage);
            }
            TextView txt = layout.findViewById(R.id.empty_net_error_tv);
            if (txt != null && mErrorText != null) {
                txt.setText(mErrorText);
            }
            layout.findViewById(R.id.empty_net_error_parent)
                  .setOnClickListener(v -> {
                      boolean connected = NetworkUtils.isConnected();
                      if (!connected) {
                          ToastUtils.INSTANCE.showShort(R.string.info_net_error);
                          return;
                      }
                      if (retryCallBack != null) {
                          retryCallBack.onRetry();
                      }
                  });
        }
        return layout;
    }

    private void show(int layoutId) {
        if (loadingView != null) {
            loadingView.stopAnimation();
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (mStaticTopId != NO_ID && getChildAt(i).getId() == mStaticTopId) {
                getChildAt(i).setVisibility(VISIBLE);
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }
        if (mContentId != 0) {
            View content = layout(mContentId);
            content.setVisibility(VISIBLE);
        }
        View view = layout(layoutId);
        view.setVisibility(VISIBLE);
        if (mLoadingResId == layoutId) {
            loadingView = view.findViewById(R.id.base_loading_view);
            loadingView.startAnimation();
        }
        if (staticTopView != null) {
            staticTopView.bringToFront();
        }
    }

    /**
     * 设置最上层永远不会遮盖的布局
     */
    public void setStaticTop(int id) {
        if (id != 0) {
            mStaticTopId = id;
            if (staticTopView == null && mContentId != NO_ID) {
                View contentView = layout(mContentId);
                staticTopView = contentView.findViewById(id);
                //FrameLayout.LayoutParams layoutParams =
                //    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                //        ViewGroup.LayoutParams.MATCH_PARENT);
                if (contentView instanceof ViewGroup) {
                    try {
                        ((ViewGroup) contentView).removeView(staticTopView);
                    } catch (Exception e) {
                    }
                }
                if (staticTopView != null) {
                    staticTopView.bringToFront();
                    addView(staticTopView);
                }
            }
        }
    }

    public MultiStatusLayout setContent(@LayoutRes int id) {
        if (mContentId != id) {
            remove(mContentId);
            mContentId = id;
            if (staticTopView == null && mStaticTopId != NO_ID) {
                staticTopView = layout(mContentId).findViewById(mStaticTopId);
                FrameLayout.LayoutParams layoutParams =
                        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                staticTopView.setLayoutParams(layoutParams);
                addView(staticTopView, 100);
            }
        }
        return this;
    }

    /**
     * 移除布局
     */
    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    public MultiStatusLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }

    public MultiStatusLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    public MultiStatusLayout setEmptyImage(@DrawableRes int resId) {
        mEmptyImage = resId;
        image(mEmptyResId, R.id.empty_no_date_image, mEmptyImage);
        return this;
    }

    private void image(int layoutId, int ctrlId, int resId) {
        if (mLayouts.containsKey(layoutId)) {
            ImageView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setImageResource(resId);
            }
        }
    }

    public MultiStatusLayout setEmptyText(String value) {
        mEmptyText = value;
        text(mEmptyResId, R.id.empty_no_data_tv, mEmptyText);
        return this;
    }

    private void text(int layoutId, int ctrlId, CharSequence value) {
        if (mLayouts.containsKey(layoutId)) {
            TextView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setText(value);
            }
        }
    }

    public MultiStatusLayout setError(@LayoutRes int id) {
        if (mErrorResId != id) {
            remove(mErrorResId);
            mErrorResId = id;
        }
        return this;
    }

    public MultiStatusLayout setErrorImage(@DrawableRes int resId) {
        mErrorImage = resId;
        image(mErrorResId, R.id.empty_net_error_image, mErrorImage);
        return this;
    }

    public MultiStatusLayout setErrorText(String value) {
        mErrorText = value;
        text(mErrorResId, R.id.empty_net_error_tv, mErrorText);
        return this;
    }

    public MultiStatusLayout setRetryCallBack(OnRetryCallBack retryCallBack) {
        this.retryCallBack = retryCallBack;
        return this;
    }

    public void hideLoading() {
        layout(mLoadingResId).setVisibility(GONE);
    }

    public void showEmpty() {
        show(mEmptyResId);
    }

    public void showError() {
        show(mErrorResId);
    }

    public void showContent() {
        show(mContentId);
    }

    /**
     * 设置除了Content外的topPadding
     */
    public void setTopPadding(int top) {
        topPadding = top;
    }

    /**
     * 设置除了Content外的topPadding
     */
    public void setBottomPadding(int top) {
        bottomPadding = top;
    }

    /**
     * 点击重试回调接口
     */
    public static interface OnRetryCallBack {
        /**
         * 重新加载
         */
        void onRetry();
    }
}
