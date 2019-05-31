package com.sorgs.baseproject.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sorgs.baseproject.utils.LogUtils

/**
 * description: Fragment基类.
 *
 * @author Sorgs.
 * Created date: 2018/12/31.
 */
abstract class BaseFragment : Fragment() {
    /**
     * Tag
     */
    protected open var TAG = javaClass.simpleName
    /**
     * 上下文
     */
    protected open var mContext: Context? = null
    protected open var mActivity: Activity? = null
    protected open lateinit var mEmptyView: View
    private var mProgressView: View? = null
    private var mLoadingView: View? = null

    /**
     * 是否初始化过布局
     */
    protected open var mIsViewInitiated: Boolean = false
    /**
     * 当前界面是否可见
     */
    protected open var mIsVisibleToUser: Boolean = false
    /**
     * 是否加载过数据
     */
    protected open var mIsDataInitiated: Boolean = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
        mActivity = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(initLayoutId(), null)
    }


    protected open fun initView(savedInstanceState: Bundle?) {
        if (mProgressView == null) {
            val rootView = mActivity!!.window.decorView as ViewGroup
            mProgressView =
                layoutInflater.inflate(com.sorgs.baseproject.R.layout.loading_layout, rootView, false)
            mLoadingView = mProgressView!!.findViewById(com.sorgs.baseproject.R.id.loading_view)
            setProgressVisible(false)
            rootView.addView(mProgressView)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState)
        mIsViewInitiated = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //fragment 是否展示出来
        mIsVisibleToUser = isVisibleToUser && isResumed
        if (isVisibleToUser) {
            prepareFetchData()
        }
    }

    /**
     * 更新数据
     *
     */
    private fun prepareFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            initData()
            initListener()
            mIsDataInitiated = true
        }
    }

    /**
     * 数据初始化操作
     */
    protected open fun initData() {

    }

    protected open fun initListener() {

    }

    /**
     * 是否展示loading
     *
     * @param show true展示
     */
    fun setProgressVisible(show: Boolean) {
        if (mProgressView != null) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
            mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            mLoadingView!!.visibility = if (show) View.VISIBLE else View.GONE
            mProgressView!!.animate()
                .setDuration(shortAnimTime.toLong())
                .alpha(
                    (if (show) 1 else 0).toFloat()
                )
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
                        mLoadingView!!.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })

        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract fun initLayoutId(): Int

    /**
     * 初始化空布局
     * 并非所有都需要
     */
    protected fun initEmpty(layoutId: Int) {
        if (layoutId == 0) {
            LogUtils.e(TAG, "layoutId is null")
            return
        }
        mEmptyView = layoutInflater.inflate(layoutId, null)
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    fun startActivity(
        clz: Class<*>,
        bundle: Bundle?
    ) {
        val intent = Intent()
        intent.setClass(activity!!, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(
        cls: Class<*>,
        bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(activity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }
}
