package com.sorgs.app;

import android.os.Bundle;

import com.sorgs.baseproject.base.JavaBaseFragment;

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
    }

    @Override
    protected boolean isNeelLoadingDialog() {
        return true;
    }

    @Override
    protected boolean isNeedShowStatusBar() {
        return true;
    }

    @Override
    protected void initListener() {
        super.initListener();
        showLoadingDialog();
        getMActivity().findViewById(R.id.tv_hello)
                      .setOnClickListener(v -> showError(R.string.info_net_error, this::dismissLoadingDialog));
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected int staticTopViewId() {
        return R.id.tv_hello;
    }
}
