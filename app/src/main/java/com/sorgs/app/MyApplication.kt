package com.sorgs.app

import android.app.Application
import com.sorgs.baseproject.base.SorgsOptions

/**
 * description: xxx.

 * @author Sorgs.
 * Created date: 2019/6/12.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SorgsOptions.init(this)
    }
}