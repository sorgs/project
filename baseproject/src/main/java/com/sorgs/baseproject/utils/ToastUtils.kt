package com.sorgs.baseproject.utils

import android.annotation.SuppressLint
import android.support.annotation.StringRes
import android.widget.Toast
import com.sorgs.baseproject.base.GlobalApplication

/**
 * description: Toast工具.
 *
 * @author Sorgs.
 */
object ToastUtils {

  private var mToast: Toast? = null
  private val mContext = GlobalApplication.mContext

  /* public static void initToastUtils(Context context) {
    mContext = context;
  }*/

  /**
   * 短Toast
   *
   * @param resId 资源文件
   */
  fun showShort(@StringRes resId: Int) {
    if (mContext != null) {
      show(mContext.getString(resId), Toast.LENGTH_SHORT)
    } else {
      throw RuntimeException("ToastUtils not initialized !")
    }
  }

  /**
   * 懒加载 Toast
   *
   * @param string 文本
   * @param lengthShort 展示长短
   */
  @SuppressLint("ShowToast")
  private fun show(string: String, lengthShort: Int) {
    if (mContext != null) {
      if (mToast == null) {
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
      }
      mToast!!.setText(string)
      mToast!!.duration = lengthShort
      mToast!!.show()
    } else {
      throw RuntimeException("ToastUtils not initialized !")
    }
  }

  /**
   * 短Toast
   *
   * @param text 文本
   */
  fun showShort(text: String) {
    show(text, Toast.LENGTH_SHORT)
  }

  /**
   * 长Toast
   *
   * @param resId 资源文件
   */
  fun showLong(@StringRes resId: Int) {
    if (mContext != null) {
      show(mContext.getString(resId), Toast.LENGTH_LONG)
    } else {
      throw RuntimeException("ToastUtils not initialized !")
    }
  }

  /**
   * 长Toast
   *
   * @param text 文本
   */
  fun showLong(text: String) {
    show(text, Toast.LENGTH_SHORT)
  }
}
