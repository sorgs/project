package com.sorgs.baseproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;
import com.sorgs.baseproject.base.GlobalApplication;

/**
 * description: Toast工具.
 *
 * @author Sorgs.
 */
public final class ToastUtils {

  private static Toast mToast;
  private static Context mContext = GlobalApplication.mContext;

 /* public static void initToastUtils(Context context) {
    mContext = context;
  }*/

  /**
   * 短Toast
   *
   * @param resId 资源文件
   */
  public static void showShort(@StringRes final int resId) {
    if (mContext != null) {
      show(mContext.getString(resId), Toast.LENGTH_SHORT);
    } else {
      throw new RuntimeException("ToastUtils not initialized !");
    }
  }

  /**
   * 懒加载 Toast
   *
   * @param string 文本
   * @param lengthShort 展示长短
   */
  @SuppressLint("ShowToast")
  private static void show(String string, int lengthShort) {
    if (mContext != null) {
      if (mToast == null) {
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
      }
      mToast.setText(string);
      mToast.setDuration(lengthShort);
      mToast.show();
    } else {
      throw new RuntimeException("ToastUtils not initialized !");
    }
  }

  /**
   * 短Toast
   *
   * @param text 文本
   */
  public static void showShort(@NonNull final String text) {
    show(text, Toast.LENGTH_SHORT);
  }

  /**
   * 长Toast
   *
   * @param resId 资源文件
   */
  public static void showLong(@StringRes final int resId) {
    if (mContext != null) {
      show(mContext.getString(resId), Toast.LENGTH_LONG);
    } else {
      throw new RuntimeException("ToastUtils not initialized !");
    }
  }

  /**
   * 长Toast
   *
   * @param text 文本
   */
  public static void showLong(@NonNull final String text) {
    show(text, Toast.LENGTH_SHORT);
  }
}
