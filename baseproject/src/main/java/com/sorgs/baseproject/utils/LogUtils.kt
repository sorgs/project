package com.sorgs.baseproject.utils

import android.util.Log

/**
 * description: logcat日志.
 *
 * @author Sorgs.
 */
object LogUtils {

  fun setLogSwitch(swithc: Boolean) {
    LOG_ENABLE = swithc
  }

  /**
   * 设为false关闭日志
   */
  private var LOG_ENABLE = true
  /**
   * 默认TAG
   */
  private val sTag = "Sorgs"

  /**
   * info
   *
   * @param msg debug log message
   */
  fun i(msg: String) {
    if (LOG_ENABLE) {
      Log.i(sTag, msg)
    }
  }

  /**
   * debug
   *
   * @param msg debug log message
   */
  fun d(msg: String) {
    if (LOG_ENABLE) {
      Log.d(sTag, msg)
    }
  }

  /**
   * error
   *
   * @param msg error log message
   */
  fun e(msg: String) {
    if (LOG_ENABLE) {
      Log.e(sTag, msg)
    }
  }

  /**
   * verbose
   *
   * @param msg verbose log message
   */
  fun v(msg: String) {
    if (LOG_ENABLE) {
      Log.v(sTag, msg)
    }
  }

  /**
   * info
   *
   * @param tag 自定义tag
   * @param msg message
   */
  fun i(tag: String, msg: String) {
    if (LOG_ENABLE) {
      Log.i(tag, msg)
    }
  }

  /**
   * debug
   *
   * @param tag 自定义tag
   * @param msg message
   */
  fun d(tag: String, msg: String) {
    if (LOG_ENABLE) {
      Log.d(tag, msg)
    }
  }

  /**
   * error
   *
   * @param tag 自定义tag
   * @param msg message
   */
  fun e(tag: String, msg: String) {
    if (LOG_ENABLE) {
      Log.e(tag, msg)
    }
  }

  /**
   * 正式环境也弹的error
   *
   * @param tag 自定义tag
   * @param msg message
   */
  fun allE(tag: String, msg: String) {
    Log.e(tag, msg)
  }

  /**
   * verbose
   *
   * @param tag 自定义tag
   * @param msg message
   */
  fun v(tag: String, msg: String) {
    if (LOG_ENABLE) {
      Log.v(tag, msg)
    }
  }
}
