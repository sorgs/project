package com.sorgs.baseproject.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.sorgs.baseproject.R
import com.sorgs.baseproject.utils.*
import com.sorgs.baseproject.widget.MultiStatusLayout

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
    protected open lateinit var mContext: Context
    protected open var mActivity: Activity? = null
    private var mProgressView: View? = null
    private var mLoadingView: View? = null
    private var rootView: View? = null
    protected open var statusLayout: MultiStatusLayout? = null

    private var isPrepared = false
    private var isFirst = true
    /**
     * 是否在ViewPage使用Fragment
     */
    open var isInViewPager = false
    /**
     * Fragment是否可见
     */
    open var isFragmentVisible = true
    /**
     * 标题栏高度 单位px
     */
    open var mTitleHeight = 0
    /**
     * 屏幕宽度
     */
    open var mScreenWidth = 0
    /**
     * 屏幕高度(包含状态栏高度但不包含底部虚拟按键高度)
     */
    open var mScreenHeight = 0
    /**
     * 屏幕状态栏高度
     */
    open var mStatusBarHeight = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = activity
        mScreenWidth = ScreenUtils.getScreenWidth()
        mScreenHeight = ScreenUtils.getScreenHeight()
        mStatusBarHeight = BarUtils.getStatusBarHeight()
        mTitleHeight =
            SizeUtils.dp2px(resources.getDimensionPixelSize(R.dimen.app_title_bar_height))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isNeedMultiStatusLayout() && initLayoutId() != 0) {
            statusLayout = MultiStatusLayout(inflater.context)
            statusLayout?.setTopPadding(customTitleHeight())
            statusLayout?.setContent(initLayoutId())
            statusLayout?.setStaticTop(staticTopViewId())
            statusLayout?.showContent()
            rootView = statusLayout
        } else {
            rootView = inflater.inflate(initLayoutId(), null)
        }
        rootView?.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }


    protected open fun initView(savedInstanceState: Bundle?) {
        if (mProgressView == null) {
            val rootView = mActivity!!.window.decorView as ViewGroup
            mProgressView =
                layoutInflater.inflate(
                    R.layout.loading_layout,
                    rootView,
                    false
                )
            mLoadingView = mProgressView!!.findViewById(R.id.loading_view)
            setProgressVisible(false)
            rootView.addView(mProgressView)
        }
    }

    /**
     * 懒加载
     */
    private fun lazyLoad() {
        if (!isInViewPager) {
            isFirst = false
            initData()
            initListener()
            return
        }
        if (!isPrepared || !isFragmentVisible || !isFirst) {
            return
        }
        isFirst = false
        initData()
        initListener()
    }

    /**
     * 视图真正可见的时候才调用
     * 一般在viewPage中才会调用
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFragmentVisible = isVisibleToUser
        isInViewPager = true
        lazyLoad()
    }

    /**
     * 是否需要多状态的View，如果不需要多状态的View可以减少层级
     *
     * @return 为true时会在layout外在封装一层MultiStatusLayout
     */
    protected open fun isNeedMultiStatusLayout(): Boolean = true


    /**
     * 是否需要显示状态栏
     *
     * @return true显示白色状态栏，false不显示状态栏
     */
    protected open fun isNeedShowStatusBar(): Boolean = false

    /**
     * 标题栏高度
     *
     * @return 返回标题栏的高度
     */
    protected open fun customTitleHeight(): Int = 0

    /**
     * 显示loading状态 动画
     * 需要多状态布局
     *
     * @param loadMessage 提示信息可以为空
     */
    open fun showLoading(loadMessage: String?) {
        if (!TextUtils.isEmpty(loadMessage)) {
            ToastUtils.showShort(loadMessage!!)
        }
        if (statusLayout != null) {
            statusLayout?.showLoading()
        }
    }

    /**
     * 显示loading状态 动画
     * 需要多状态布局
     *
     * @param loadMessage 提示信息可以为空
     */
    open fun showLoading(loadMessage: Int?) {
        if (loadMessage != 0) {
            ToastUtils.showShort(loadMessage!!)
        }
        if (statusLayout != null) {
            statusLayout?.showLoading()
        }
    }

    /**
     * 隐藏LoadingView，如果直接显示内容或者空状态或者错误状态可以不用调用 动画
     * 需要多状态布局
     */
    open fun hideLoading() {
        if (statusLayout != null) {
            statusLayout?.hideLoading()
        }
    }

    /**
     * 数据加载成功显示内容
     * 需要多状态布局
     */
    open fun showContent() {
        if (statusLayout != null) {
            statusLayout?.showContent()
        }
    }

    /**
     * 显示错误状态
     * 需要多状态布局
     *
     * @param message       提示信息
     * @param retryCallBack 错误的操作回调
     */
    open fun showError(message: String, retryCallBack: MultiStatusLayout.OnRetryCallBack?) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShort(message)
        }
        if (statusLayout != null) {
            statusLayout?.setRetryCallBack(retryCallBack)
            statusLayout?.showError()
        }
    }

    /**
     * 显示错误状态
     * 需要多状态布局
     *
     * @param message       提示信息
     * @param retryCallBack 错误的操作回调
     */
    open fun showError(message: Int?, retryCallBack: MultiStatusLayout.OnRetryCallBack?) {
        if (message != 0) {
            ToastUtils.showShort(message!!)
        }
        statusLayout?.setRetryCallBack(retryCallBack)
        statusLayout?.showError()
    }


    /**
     * 显示数据为空的状态
     * 需要多状态布局
     */
    open fun showEmpty() {
        if (statusLayout != null) {
            statusLayout?.showEmpty()
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


    /**
     * 检查网络是否可用(点击的时候调用)
     */
    open fun checkNetWork(): Boolean {
        val connected: Boolean = NetworkUtils.isConnected()
        if (!connected) {
            ToastUtils.showShort(R.string.info_net_error)
        }
        return connected
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract fun initLayoutId(): Int


    /**
     * 不管状态一直在上层显示的View的ID
     */
    protected open fun staticTopViewId(): Int = 0


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
