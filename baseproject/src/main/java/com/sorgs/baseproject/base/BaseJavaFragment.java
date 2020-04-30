package com.sorgs.baseproject.base;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: xxx.
 *
 * @author Sorgs.
 * Created date: 2020/4/30.
 */
public abstract class BaseJavaFragment<A extends BaseActivity> extends BaseFragment<A> {

    private Unbinder mUnBinder;

    @Override
    public void onDetach() {
        super.onDetach();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }

    @Override
    protected void initFragment() {
        //绑定到butterKnife
        mUnBinder = ButterKnife.bind(this, getView());
        super.initFragment();
    }
}
