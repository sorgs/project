package com.sorgs.app

import com.sorgs.baseproject.base.JavaBaseActivity
import com.sorgs.baseproject.utils.singleClick
import com.sorgs.baseproject.widget.MultiStatusLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author sorgs
 */
class MainActivity : JavaBaseActivity() {
    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun staticTopViewId() = R.id.ll_test

    override fun isNeedShowStatusBar(): Boolean = true

    override fun initData() {
        super.initData()
        tv_hello.singleClick {
            showError(R.string.info_net_error,
                MultiStatusLayout.OnRetryCallBack { showContent() })
        }
    }
}