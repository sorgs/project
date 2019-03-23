package com.sorgs.baseproject.utils;

import android.util.Log;
import com.sorgs.baseproject.BuildConfig;

/**
 * description: logcat日志.
 *
 * @author Sorgs.
 */
public final class LogUtils {
  /**
   * 设为false关闭日志
   */
  private final static boolean LOG_ENABLE = BuildConfig.DEBUG;
  /**
   * 默认TAG
   */
  private static String sTag = "Sorgs";

  /**
   * info
   *
   * @param msg debug log message
   */
  public static void i(String msg) {
    if (LOG_ENABLE) {
      Log.i(sTag, msg);
    }
  }

  /**
   * debug
   *
   * @param msg debug log message
   */
  public static void d(String msg) {
    if (LOG_ENABLE) {
      Log.d(sTag, msg);
    }
  }

  /**
   * error
   *
   * @param msg error log message
   */
  public static void e(String msg) {
    if (LOG_ENABLE) {
      Log.e(sTag, msg);
    }
  }

  /**
   * verbose
   *
   * @param msg verbose log message
   */
  public static void v(String msg) {
    if (LOG_ENABLE) {
      Log.v(sTag, msg);
    }
  }

  /**
   * info
   *
   * @param tag 自定义tag
   * @param msg message
   */
  public static void i(String tag, String msg) {
    if (LOG_ENABLE) {
      Log.i(tag, msg);
    }
  }

  /**
   * debug
   *
   * @param tag 自定义tag
   * @param msg message
   */
  public static void d(String tag, String msg) {
    if (LOG_ENABLE) {
      Log.d(tag, msg);
    }
  }

  /**
   * error
   *
   * @param tag 自定义tag
   * @param msg message
   */
  public static void e(String tag, String msg) {
    if (LOG_ENABLE) {
      Log.e(tag, msg);
    }
  }

  /**
   * 正式环境也弹的error
   *
   * @param tag 自定义tag
   * @param msg message
   */
  public static void allE(String tag, String msg) {
    Log.e(tag, msg);
  }

  /**
   * verbose
   *
   * @param tag 自定义tag
   * @param msg message
   */
  public static void v(String tag, String msg) {
    if (LOG_ENABLE) {
      Log.v(tag, msg);
    }
  }
}
