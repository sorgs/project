package com.sorgs.baseproject.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.sorgs.baseproject.R
import com.sorgs.baseproject.utils.LogUtils

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
  protected var TAG = javaClass.simpleName
  /**
   * 上下文
   */
  protected lateinit var mContext: Context
  protected lateinit var mEmptyView: View
  /**
   * 展示错误布局，是否可以点击重试
   */
  protected var mCanRetry: Boolean = false
  private var mProgressView: View? = null
  private var mLoadingView: View? = null

  @SuppressLint("InlinedApi")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val layoutResID = initLayoutId()
    //如果initView返回0,则不会调用setContentView()
    if (layoutResID != 0) {
      setContentView(layoutResID)
    }
    //修改标题栏颜色为暗色主题颜色（黑色）
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    mContext = this
    initView()
    initData()
    initListener()
  }

  /**
   * 添加布局文件
   *
   * @return 布局文件
   */
  protected abstract fun initLayoutId(): Int

  protected fun initView() {
    if (mProgressView == null) {
      val rootView = window.decorView as ViewGroup
      mProgressView = layoutInflater.inflate(R.layout.loading_layout, rootView, false)
      mLoadingView = mProgressView!!.findViewById(R.id.loading_view)
      setProgressVisible(false)
      rootView.addView(mProgressView)
    }
  }

  protected fun initData() {

  }

  protected fun initListener() {

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

  override fun onDestroy() {
    super.onDestroy()
  }

  /**
   * 初始化空布局
   * 并非所有都需要
   */
  @SuppressLint("InflateParams")
  protected fun initEmpty(layoutId: Int) {
    if (layoutId == 0) {
      LogUtils.e(TAG, "layoutId is null")
      return
    }
    mEmptyView = layoutInflater.inflate(layoutId, null)
  }

}
