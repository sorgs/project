package com.sorgs.baseproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/05
 * desc   : 图片加载适配器
 */
public final class ImagePagerAdapter extends PagerAdapter
        implements View.OnClickListener {

    private final Activity mActivity;
    private final List<String> mData;

    public ImagePagerAdapter(Activity activity, List<String> data) {
        mActivity = activity;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView view = new PhotoView(mActivity);
        view.setOnClickListener(this);

        Glide.with(container.getContext())
             .load(mData.get(position))
             .into(view);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void onClick(View v) {
        // 单击图片退出当前的 Activity
        if (!mActivity.isFinishing()) {
            mActivity.finish();
        }
    }
}