package com.sorgs.baseproject.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.sorgs.baseproject.R
import com.sorgs.baseproject.utils.*
import com.sorgs.baseproject.widget.LoadingDialog
import com.sorgs.baseproject.widget.MultiStatusLayout

/**
 * description: Activity基本.
 *
 * @author Sorgs.
 * Created date: 2018/12/31.
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * Tag
     */
    protected open var TAG = javaClass.simpleName
    /**
     * 上下文
     */
    protected open lateinit var mContext: Context
    private var mProgressView: View? = null
    private var mLoadingView: View? = null

    /**
     * 整体布局
     */
    private var rootView: View? = null

    /**
     * 屏幕状态栏高度
     */
    var mStatusBarHeight = 0

    /**
     * 屏幕宽度
     */
    var mScreenWidth = 0
    /**
     * 屏幕高度(包含状态栏高度但不包含底部虚拟按键高度)
     */
    protected open var mScreenHeight = 0

    /**
     * 标题栏高度
     */
    protected open var mTitleHeight = 0

    protected open lateinit var mActivity: Activity

    /**
     * 多状态布局
     */
    protected open var mStatusLayout: MultiStatusLayout? = null

    /**
     * 非全屏loading
     */
    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initDefaultData()
        initStatus()
        super.onCreate(savedInstanceState)
        val layoutResID = initLayoutId()
        //如果initView返回0,则不会调用setContentView()
        if (isNeedMultiStatusLayout()) {
            mStatusLayout = MultiStatusLayout(this)
            //可以进行一些MultiStatusLayout的配置
            mStatusLayout?.setContent(layoutResID)
            mStatusLayout?.setStaticTop(staticTopViewId())
            rootView = mStatusLayout
            mStatusLayout?.showContent()
        } else {
            rootView = layoutInflater.inflate(layoutResID, null)
        }
        setContentView(rootView)
        if (statusBarColor() != Color.TRANSPARENT &&
            (isNeedShowStatusBar() || customTitleHeight() != 0)
        ) {
            rootView!!.setPadding(0, mStatusBarHeight, 0, 0)
        }
        initView(savedInstanceState)
        initData()
        initListener()
    }


    /**
     * 初始化部分成员变量
     */
    private fun initDefaultData() {
        //公用成员变量初始化
        mActivity = this
        mContext = this
        mScreenWidth = ScreenUtils.getScreenWidth()
        mScreenHeight = ScreenUtils.getScreenHeight()
        mStatusBarHeight = BarUtils.getStatusBarHeight()
        mTitleHeight =
            SizeUtils.dp2px(resources.getDimensionPixelSize(R.dimen.app_title_bar_height))
    }


    /**
     * 适配状态栏
     */
    protected open fun initStatus() {
        val window = window
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (customTitleHeight() != 0 || isNeedShowStatusBar()) {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = statusBarColor()
            }

        }
    }

    /**
     * 是否需要显示状态栏
     *
     * @return true显示状态栏，false不显示状态栏
     */
    protected open fun isNeedShowStatusBar(): Boolean = false

    /**
     * 状态栏颜色,默认不填充状态栏
     */
    protected open fun statusBarColor(): Int = Color.TRANSPARENT


    /**
     * 标题栏高度(如果不需要状态栏需要重写statusBarColor()方法)
     *
     * @return 返回标题栏的高度
     */
    protected open fun customTitleHeight(): Int = 0

    /**
     * 是否需要多状态的View，如果不需要多状态的View可以减少层级，默认是需要
     * 如果页面不需要可以重写方法返回false
     *
     * @return 为true时会在layout外在封装一层MultiStatusLayout
     */
    protected open fun isNeedMultiStatusLayout(): Boolean = true

    /**
     * 不管状态一直在上层显示的View的ID
     */
    protected open fun staticTopViewId(): Int = 0

    /**
     * 添加布局文件
     *
     * @return 布局文件
     */
    protected abstract fun initLayoutId(): Int

    protected open fun initView(savedInstanceState: Bundle?) {
        if (isNeelLoadingDialog()) {
            mLoadingDialog = LoadingDialog(mContext)
        }
    }

    /**
     * 是否需要LoadingDialog,默认不需要
     */
    protected open fun isNeelLoadingDialog(): Boolean = false


    protected open fun initData() {

    }

    protected open fun initListener() {

    }

    /**
     * 是否展示loading 转圈
     *
     * @param show true展示
     */
    fun setProgressVisible(show: Boolean) {
        if (mProgressView == null) {
            val rootView = window.decorView as ViewGroup
            mProgressView = layoutInflater.inflate(R.layout.loading_layout, rootView, false)
            mLoadingView = mProgressView?.findViewById(R.id.loading_view)
            rootView.addView(mProgressView)
        }
        if (mProgressView != null) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
            mProgressView?.visibility = if (show) View.VISIBLE else View.GONE
            mLoadingView?.visibility = if (show) View.VISIBLE else View.GONE
            mProgressView?.animate()
                ?.setDuration(shortAnimTime.toLong())
                ?.alpha(
                    (if (show) 1 else 0).toFloat()
                )
                ?.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        mProgressView?.visibility = if (show) View.VISIBLE else View.GONE
                        mLoadingView?.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })

        }
    }

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
        mStatusLayout?.showLoading()
    }

    /**
     * 显示LoadingDialog
     */
    open fun showLoadingDialog() {
        if (!mLoadingDialog?.isShowing!!) {
            mLoadingDialog?.show()
        }
    }

    /**
     * 隐藏LoadingDialog
     */
    open fun dismissLoadingDialog() {
        mLoadingDialog?.dismiss()
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
        mStatusLayout?.showLoading()
    }

    /**
     * 隐藏LoadingView，如果直接显示内容或者空状态或者错误状态可以不用调用 动画
     * 需要多状态布局
     */
    open fun hideLoading() {
        mStatusLayout?.hideLoading()
    }

    /**
     * 数据加载成功显示内容
     * 需要多状态布局
     */
    open fun showContent() {
        mStatusLayout?.showContent()
    }

    /**
     * 显示错误状态
     * 需要多状态布局
     *
     * @param message       提示信息
     * @param retryCallBack 错误的操作回调
     */
    open fun showError(message: String?, retryCallBack: MultiStatusLayout.OnRetryCallBack?) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShort(message!!)
        }
        mStatusLayout?.setRetryCallBack(retryCallBack)
        mStatusLayout?.showError()
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
        mStatusLayout?.setRetryCallBack(retryCallBack)
        mStatusLayout?.showError()
    }

    /**
     * 显示数据为空的状态
     * 需要多状态布局
     */
    open fun showEmpty() {
        mStatusLayout?.showEmpty()
    }


    //检查网络是否可用(点击的时候调用)
    open fun checkNetWork(): Boolean {
        val connected: Boolean = NetworkUtils.isConnected()
        if (!connected) {
            ToastUtils.showShort(R.string.info_net_error)
        }
        return connected
    }

}
