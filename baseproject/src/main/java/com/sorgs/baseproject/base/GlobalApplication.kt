package com.sorgs.baseproject.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * description: appliction.
 *
 * @author Sorgs.
 * Created date: 2018/12/31.
 */
open class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }
}
