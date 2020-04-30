package com.sorgs.baseproject.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.sorgs.baseproject.action.BundleAction
import com.sorgs.baseproject.action.ClickAction
import com.sorgs.baseproject.action.TitleBarAction
import com.sorgs.baseproject.dialog.LoadingDialog
import com.sorgs.baseproject.dialog.WaitDialog
import com.sorgs.baseproject.utils.ToastUtils
import com.sorgs.baseproject.utils.singleClick
import java.util.*
import kotlin.math.pow

/**
 * description: Activity基本.
 *
 * @author Sorgs.
 * Created date: 2018/12/31.
 */
abstract class BaseActivity : AppCompatActivity(), ClickAction, BundleAction, TitleBarAction {
    /**
     * Tag
     */
    protected open var TAG = javaClass.simpleName

    /**
     * 上下文
     */
    protected open lateinit var mContext: Context

    /**
     * startActivityForResult 方法优化
     */
    private var mActivityCallback: OnActivityCallback? = null

    private var mActivityRequestCode = 0

    /**
     * 标题栏对象
     */
    private var mTitleBar: TitleBar? = null

    /**
     * 状态栏沉浸
     */
    private var mImmersionBar: ImmersionBar? = null

    /**
     * 加载对话框
     */
    private var mLoadingDialog: BaseDialog? = null

    private var mLoadingView: BaseDialog? = null

    /**
     * 对话框数量
     */
    private var mDialogTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initLayout()
        initView(savedInstanceState)
        initData()
        initListener()
    }

    /**
     * 初始化布局
     */
    protected open fun initLayout() {
        if (initLayoutId() > 0) {
            setContentView(initLayoutId())
            initSoftKeyboard()
        }
        titleBar?.setOnTitleBarListener(this)
        initImmersion()
    }

    override fun getTitleBar(): TitleBar? {
        if (mTitleBar == null) {
            mTitleBar = findTitleBar(getContentView())
        }
        return mTitleBar
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    open fun getStatusBarConfig(): ImmersionBar? {
        return mImmersionBar
    }

    override fun onLeftClick(v: View?) {
        onBackPressed()
    }

    /**
     * 初始化沉浸式
     */
    protected open fun initImmersion() {
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            createStatusBarConfig()?.init()

            // 设置标题栏沉浸
            if (mTitleBar != null) {
                ImmersionBar.setTitleBar(this, mTitleBar)
            }
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected open fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun finish() {
        hideSoftKeyboard()
        super.finish()
        //overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    /**
     * 设置标题栏的标题
     */
    override fun setTitle(@StringRes id: Int) {
        title = getString(id)
    }


    /**
     * 设置标题栏的标题
     */
    override fun setTitle(title: CharSequence?) {
        if (mTitleBar != null) {
            mTitleBar!!.title = title
        }
    }


    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar? {
        // 在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
        return mImmersionBar
    }

    /**
     * 状态栏字体深色模式
     */
    protected open fun isStatusBarDarkFont(): Boolean {
        return true
    }


    override fun onDestroy() {
        if (isShowLoadingDialog()) {
            mLoadingDialog!!.dismiss()
        }
        mLoadingDialog = null
        if (isShowLoadingView()) {
            mLoadingView!!.dismiss()
        }
        mLoadingView = null
        super.onDestroy()
    }

    /**
     * 当前加载对话框是否在显示中
     */
    open fun isShowLoadingDialog(): Boolean {
        return mLoadingDialog != null && mLoadingDialog!!.isShowing
    }

    /**
     * 当前加载动画是否在显示中
     */
    open fun isShowLoadingView(): Boolean {
        return mLoadingView != null && mLoadingView!!.isShowing
    }


    /**
     * 显示加载对话框
     */
    open fun showLodingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = WaitDialog.Builder(this)
                    .setCancelable(false)
                    .create()
        }
        if (!mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.show()
        }
        mDialogTotal++
    }

    /**
     * 显示加载
     */
    open fun showLodingView() {
        if (mLoadingView == null) {
            mLoadingView = LoadingDialog.Builder(this)
                    .setCancelable(false)
                    .create()
        }
        if (!mLoadingView!!.isShowing) {
            mLoadingView!!.show()
        }
        mDialogTotal++
    }

    /**
     * 隐藏加载对话框
     */
    open fun hideLoadingDialog() {
        if (mDialogTotal == 1) {
            mLoadingDialog?.dismiss()
        }
        if (mDialogTotal > 0) {
            mDialogTotal--
        }
    }

    /**
     * 隐藏加载对话框
     */
    open fun hideLoadingView() {
        if (mDialogTotal == 1) {
            mLoadingView?.dismiss()
        }
        if (mDialogTotal > 0) {
            mDialogTotal--
        }
    }

    /**
     * 初始化软键盘
     */
    protected open fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView()?.singleClick { hideSoftKeyboard() }
    }


    /**
     * 和 setContentView 对应的方法
     */
    open fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    /**
     * 添加布局文件
     *
     * @return 布局文件
     */
    protected abstract fun initLayoutId(): Int

    protected open fun initView(savedInstanceState: Bundle?) {
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
     * 显示loading状态 动画
     * 需要多状态布局
     *
     * @param loadMessage 提示信息可以为空
     */
    open fun showLoading(loadMessage: String?) {
        if (!TextUtils.isEmpty(loadMessage)) {
            ToastUtils.showShort(loadMessage!!)
        }
    }

    override fun getBundle(): Bundle? {
        return intent.extras
    }

    /**
     * 获取当前 Activity 对象
     */
    protected open fun getActivity(): BaseActivity? {
        return this
    }

    /**
     * startActivity 方法简化
     */
    open fun startActivity(clazz: Class<out Activity?>?) {
        startActivity(Intent(this, clazz))
    }

    open fun startActivityForResult(clazz: Class<out Activity?>?, callback: OnActivityCallback) {
        startActivityForResult(Intent(this, clazz), null, callback)
    }

    open fun startActivityForResult(intent: Intent?, options: Bundle?,
                                    callback: OnActivityCallback) {
        // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
        if (mActivityCallback == null) {
            mActivityCallback = callback
            // 随机生成请求码，这个请求码必须在 2 的 16 次幂以内，也就是 0 - 65535
            mActivityRequestCode = Random().nextInt(2.0.pow(16.0).toInt())
            startActivityForResult(intent, mActivityRequestCode, options)
        }
    }

    open fun startActivityForResult(intent: Intent?, callback: OnActivityCallback) {
        startActivityForResult(intent, null, callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback!!.onActivityResult(resultCode, data)
            mActivityCallback = null
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        //overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        hideSoftKeyboard()
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options)
        //overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }


    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        val view = currentFocus
        if (view != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    interface OnActivityCallback {
        /**
         * 结果回调
         *
         * @param resultCode 结果码
         * @param data       数据
         */
        fun onActivityResult(resultCode: Int, data: Intent?)
    }

}
