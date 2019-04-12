package com.sorgs.baseproject.base

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * description: 带ButterKnife的基类.
 *
 * @author Sorgs.
 * Created date: 2019/4/12.
 */
abstract class JavaBaseActivity : BaseActivity() {

    private var mUnBinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (initLayoutId() != 0) {
            //绑定到butterKnife
            mUnBinder = ButterKnife.bind(this)
        }
        super.onCreate(savedInstanceState)
    }


    abstract override fun initLayoutId(): Int

    override fun onDestroy() {
        if (mUnBinder != null && mUnBinder !== Unbinder.EMPTY) {
            mUnBinder?.unbind()
        }
        super.onDestroy()
    }
}
