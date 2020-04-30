package com.sorgs.baseproject.base

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * description: Activity基类，带ButterKnife.
 *
 * @author Sorgs.
 * Created date: 2019/12/24.
 */
abstract class BaseJavaActivity : BaseActivity() {
    private var mUnBinder: Unbinder? = null

    override fun initLayout() {
        //绑定到butterKnife
        mUnBinder = ButterKnife.bind(this)
        super.initLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnBinder != null && mUnBinder !== Unbinder.EMPTY) {
            mUnBinder?.unbind()
        }
    }
}
