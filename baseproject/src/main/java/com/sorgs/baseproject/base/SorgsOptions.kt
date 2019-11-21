package com.sorgs.baseproject.base

import android.annotation.SuppressLint
import android.app.Application
import com.sorgs.baseproject.utils.BarUtils

/**
 * description: appliction.
 *
 * @author Sorgs.
 * Created date: 2018/12/31.
 */
open class SorgsOptions {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mApplication: Application

        /**
         * 状态栏高度
         */
        var mStatusHeight: Int = 0

        fun init(application: Application) {
            mApplication = application
            mStatusHeight = BarUtils.getStatusBarHeight()
        }
    }
}
