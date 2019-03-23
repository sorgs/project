package com.sorgs.baseproject.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.sorgs.baseproject.base.GlobalApplication

class KeyboardUtils private constructor() {

  init {
    throw UnsupportedOperationException("u can't instantiate me...")
  }

  interface OnSoftInputChangedListener {
    fun onSoftInputChanged(height: Int)
  }

  companion object {

    private var sContentViewInvisibleHeightPre: Int = 0
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null
    private var onSoftInputChangedListener: OnSoftInputChangedListener? = null

    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    fun showSoftInput(activity: Activity) {
      val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      var view = activity.currentFocus
      if (view == null) {
        view = View(activity)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
      }
      imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    fun showSoftInput(view: View) {
      val imm = GlobalApplication.mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      view.isFocusable = true
      view.isFocusableInTouchMode = true
      view.requestFocus()
      imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    fun hideSoftInput(activity: Activity?) {
      if (activity == null) {
        return
      }
      val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      var view = activity.currentFocus
      if (view == null) {
        view = View(activity)
      }
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    fun hideSoftInput(view: View) {
      val imm = GlobalApplication.mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Toggle the soft input display or not.
     */
    fun toggleSoftInput() {
      val imm = GlobalApplication.mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Return whether soft input is visible.
     *
     * @param activity The activity.
     * @param minHeightOfSoftInput The minimum height of soft input.
     * @return `true`: yes<br></br>`false`: no
     */
    @JvmOverloads fun isSoftInputVisible(
      activity: Activity,
      minHeightOfSoftInput: Int = 200
    ): Boolean {
      return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput
    }

    private fun getContentViewInvisibleHeight(activity: Activity): Int {
      val contentView = activity.findViewById<View>(android.R.id.content)
      val outRect = Rect()
      contentView.getWindowVisibleDisplayFrame(outRect)
      return contentView.bottom - outRect.bottom
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    fun registerSoftInputChangedListener(
      activity: Activity,
      listener: OnSoftInputChangedListener
    ) {
      val flags = activity.window.attributes.flags
      if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
      }
      val contentView = activity.findViewById<View>(android.R.id.content)
      sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity)
      onSoftInputChangedListener = listener
      onGlobalLayoutListener = OnGlobalLayoutListener {
        if (onSoftInputChangedListener != null) {
          val height = getContentViewInvisibleHeight(activity)
          if (sContentViewInvisibleHeightPre != height) {
            onSoftInputChangedListener!!.onSoftInputChanged(height)
            sContentViewInvisibleHeightPre = height
          }
        }
      }
      contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun unregisterSoftInputChangedListener(activity: Activity) {
      val contentView = activity.findViewById<View>(android.R.id.content)
      contentView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
      onSoftInputChangedListener = null
      onGlobalLayoutListener = null
    }

    /**
     * Fix the leaks of soft input.
     *
     * Call the function in [Activity.onDestroy].
     *
     * @param mContext The mContext.
     */
    fun fixSoftInputLeaks(mContext: Context?) {
      if (mContext == null) {
        return
      }
      val imm = GlobalApplication.mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
      val strArr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
      for (i in 0..2) {
        try {
          val declaredField = imm.javaClass.getDeclaredField(strArr[i]) ?: continue
          if (!declaredField.isAccessible) {
            declaredField.isAccessible = true
          }
          val obj = declaredField.get(imm)
          if (obj == null || obj !is View) {
            continue
          }
          if (obj.context === mContext) {
            declaredField.set(imm, null)
          } else {
            return
          }
        } catch (th: Throwable) {
          th.printStackTrace()
        }

      }
    }

    /**
     * Click blankj area to hide soft input.
     *
     * Copy the following code in ur activity.
     */
    fun clickBlankArea2HideSoftInput() {
      Log.i("KeyboardUtils", "Please refer to the following code.")
    }

    fun dispatchKeyhideSoftInput(ev: MotionEvent, activity: Activity): Boolean {
      if (ev.action == MotionEvent.ACTION_DOWN) {
        val v = activity.currentFocus
        if (isShouldHideKeyboard(v, ev)) {
          val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
          imm?.hideSoftInputFromWindow(
            v!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
          )
        }
      }
      return true
    }

    // Return whether touch the view.
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
      if (v != null && v is EditText) {
        val l = intArrayOf(0, 0)
        v.getLocationInWindow(l)
        val left = l[0]
        val top = l[1]
        val bottom = top + v.height
        val right = left + v.width
        return !(event.x > left && event.x < right
            && event.y > top && event.y < bottom)
      }
      return false
    }
  }
}
/**
 * Return whether soft input is visible.
 *
 * The minimum height is 200
 *
 * @param activity The activity.
 * @return `true`: yes<br></br>`false`: no
 */
