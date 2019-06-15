package com.sorgs.baseproject.utils


import android.os.Handler
import android.os.Looper
import android.os.Message

import java.lang.ref.WeakReference

/**
 * description: 实现回调弱引用的Handler
 * 防止由于内部持有导致的内存泄
 * 传入的Callback不能使用匿名实现的变量
 * 必须与使用这个Handle的对象的生命周期一致否则会被立即释放掉了
 *
 * @author Sorgs.
 * Created date: 2019/6/13.
 */
class WeakRefHandler : Handler {

    private var mWeakRefHandler: WeakReference<Callback>? = null

    constructor(callback: Callback) {
        mWeakRefHandler = WeakReference(callback)
    }

    constructor(callback: Callback, looper: Looper) : super(looper) {
        mWeakRefHandler = WeakReference(callback)
    }

    override fun handleMessage(msg: Message) {
        if (mWeakRefHandler != null && mWeakRefHandler!!.get() != null) {
            val callback = mWeakRefHandler!!.get()
            callback?.handleMessage(msg)
        }
    }
}
