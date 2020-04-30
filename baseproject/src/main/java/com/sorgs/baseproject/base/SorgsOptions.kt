package com.sorgs.baseproject.base

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.hjq.bar.TitleBar
import com.hjq.bar.style.TitleBarLightStyle
import com.sorgs.baseproject.R
import com.sorgs.baseproject.helper.ActivityStackManager
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
        lateinit var mContext: Application

        /**
         * 状态栏高度
         */
        var mStatusHeight: Int = 0

        fun init(application: Application) {
            mContext = application
            mStatusHeight = BarUtils.getStatusBarHeight()
            // Activity 栈管理初始化
            ActivityStackManager.getInstance().init(application)

            // 标题栏全局样式
            TitleBar.initStyle(object : TitleBarLightStyle(application) {
                override fun getBackground(): Drawable {
                    return ColorDrawable(getColor(R.color.colorPrimary))
                }

                override fun getBackIcon(): Drawable {
                    return getDrawable(R.drawable.ic_back_black)
                }
            })
        }
    }
}
