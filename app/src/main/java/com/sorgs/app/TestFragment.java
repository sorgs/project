package com.sorgs.app;

import android.os.Bundle;
import android.widget.ImageView;

import com.sorgs.baseproject.base.JavaBaseFragment;
import com.sorgs.baseproject.imageloader.PicLoader;

import org.jetbrains.annotations.Nullable;

/**
 * description: xxx.
 *
 * @author Sorgs.
 * Created date: 2019/5/31.
 */
public class TestFragment extends JavaBaseFragment {

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ImageView imageView = getView().findViewById(R.id.iv_hello);
        PicLoader.INSTANCE.loadImage(mContext,
                "http://bmob.files.emoticon.sorgs.cn/2019/12/12/396d4ed13e13463ca59989db309defe4.gif", imageView,
                R.mipmap.ic_launcher);
    }

    @Override
    protected boolean isNeelLoadingDialog() {
        return false;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_test;
    }

}
