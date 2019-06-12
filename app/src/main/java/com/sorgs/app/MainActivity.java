package com.sorgs.app;

import android.view.View;

import com.sorgs.baseproject.base.JavaBaseActivity;
import com.sorgs.baseproject.utils.ToastUtils;

/**
 * @author sorgs.li
 */
public class MainActivity extends JavaBaseActivity {


    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.INSTANCE.showShort("hello");
            }
        });
    }
}
