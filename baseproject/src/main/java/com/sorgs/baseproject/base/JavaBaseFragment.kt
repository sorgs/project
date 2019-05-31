package com.sorgs.baseproject.base

import android.os.Bundle
import android.view.View

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * description: Fragment基类,带ButterKnife.
 *
 * @author Sorgs.
 * Created date: 2019/4/14.
 */
abstract class JavaBaseFragment : BaseFragment() {
    private var mUnBinder: Unbinder? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mUnBinder = ButterKnife.bind(this, view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mUnBinder != null && mUnBinder !== Unbinder.EMPTY) {
            mUnBinder?.unbind()
        }
    }

}
