package com.sorgs.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.rd.PageIndicatorView;
import com.sorgs.baseproject.R;
import com.sorgs.baseproject.adapter.ImagePagerAdapter;
import com.sorgs.baseproject.base.BaseActivity;
import com.sorgs.baseproject.other.IntentKey;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/05
 * desc   : 查看大图
 */
public final class ImageActivity extends BaseActivity {

    ViewPager mViewPager;
    PageIndicatorView mIndicatorView;

    public static void start(Context context, String url) {
        ArrayList<String> images = new ArrayList<>(1);
        images.add(url);
        start(context, images);
    }

    public static void start(Context context, ArrayList<String> urls) {
        start(context, urls, 0);
    }

    public static void start(Context context, ArrayList<String> urls, int index) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(IntentKey.PICTURE, urls);
        intent.putExtra(IntentKey.INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                    // 有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                    .fullScreen(true)
                    // 隐藏状态栏
                    .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                    // 透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                    .transparentNavigationBar();
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewPager = findViewById(R.id.vp_image_pager);
        mIndicatorView = findViewById(R.id.pv_image_indicator);
        mIndicatorView.setViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        ArrayList<String> images = getStringArrayList(IntentKey.PICTURE);
        int index = getInt(IntentKey.INDEX);
        if (images != null && images.size() > 0) {
            mViewPager.setAdapter(new ImagePagerAdapter(this, images));
            if (index != 0 && index <= images.size()) {
                mViewPager.setCurrentItem(index);
            }
        } else {
            finish();
        }
    }
}