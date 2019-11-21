package com.sorgs.baseproject.base

import android.annotation.SuppressLint
import android.content.Context
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
        lateinit var mContext: Context

        /**
         * 状态栏高度
         */
        var mStatusHeight: Int = 0

        fun init(context: Context) {
            mContext = context
            mStatusHeight = BarUtils.getStatusBarHeight()
        }
    }
}
