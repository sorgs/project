package com.sorgs.app

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.sorgs.baseproject.base.JavaBaseActivity


/**
 * @author sorgs
 */
class MainActivity : JavaBaseActivity() {
    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }

    //override fun staticTopViewId() = R.id.ll_test

    //override fun isNeedShowStatusBar(): Boolean = true


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val fragment1 = TestFragment()
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.add(R.id.ll_test, fragment1).commit()
    }
}