package com.sorgs.baseproject.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityGroup
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.sorgs.baseproject.base.GlobalApplication

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about size
</pre> *
 */
class SizeUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    interface onGetSizeListener {
        fun onGetSize(view: View)
    }

    companion object {

        /**
         * Value of dp to value of px.
         *
         * @param dpValue The value of dp.
         * @return value of px
         */
        fun dp2px(dpValue: Float): Int {
            val scale = GlobalApplication.mContext.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * Value of px to value of dp.
         *
         * @param pxValue The value of px.
         * @return value of dp
         */
        fun px2dp(pxValue: Float): Int {
            val scale = GlobalApplication.mContext.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * Value of sp to value of px.
         *
         * @param spValue The value of sp.
         * @return value of px
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = GlobalApplication.mContext.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * Value of px to value of sp.
         *
         * @param pxValue The value of px.
         * @return value of sp
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = GlobalApplication.mContext.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * Converts an unpacked complex data value holding a dimension to its final floating
         * point value. The two parameters <var>unit</var> and <var>value</var>
         * are as in [TypedValue.TYPE_DIMENSION].
         *
         * @param value The value to apply the unit to.
         * @param unit  The unit to convert from.
         * @return The complex floating point value multiplied by the appropriate
         * metrics depending on its unit.
         */
        fun applyDimension(
            value: Float,
            unit: Int
        ): Float {
            val metrics = GlobalApplication.mContext.resources.displayMetrics
            when (unit) {
                TypedValue.COMPLEX_UNIT_PX -> return value
                TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
                TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
                TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
                TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
                TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
            }
            return 0f
        }

        /**
         * Force get the size of view.
         *
         * e.g.
         * <pre>
         * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
         * Override
         * public void onGetSize(final View view) {
         * view.getWidth();
         * }
         * });
        </pre> *
         *
         * @param view     The view.
         * @param listener The get size listener.
         */
        fun forceGetViewSize(
            view: View,
            listener: onGetSizeListener?
        ) {
            view.post {
                listener?.onGetSize(view)
            }
        }

        /**
         * Return the width of view.
         *
         * @param view The view.
         * @return the width of view
         */
        fun getMeasuredWidth(view: View): Int {
            return measureView(view)[0]
        }

        /**
         * Return the height of view.
         *
         * @param view The view.
         * @return the height of view
         */
        fun getMeasuredHeight(view: View): Int {
            return measureView(view)[1]
        }

        /**
         * Measure the view.
         *
         * @param view The view.
         * @return arr[0]: view's width, arr[1]: view's height
         */
        fun measureView(view: View): IntArray {
            var lp: ViewGroup.LayoutParams? = view.layoutParams
            if (lp == null) {
                lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
            val lpHeight = lp.height
            val heightSpec: Int
            if (lpHeight > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }
            view.measure(widthSpec, heightSpec)
            return intArrayOf(view.measuredWidth, view.measuredHeight)
        }

        @SuppressLint("PrivateApi")
                /**
                 * 获得状态栏的高度
                 */
        fun getStatusHeight(mContext: Context): Int {

            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(
                    clazz.getField("status_bar_height")
                        .get(`object`).toString()
                )
                statusHeight = mContext.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

        /**
         * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
         */
        fun getActionBarHeight(mContext: Context): Int {
            var actionBarHeight = 0
            if (mContext is AppCompatActivity && mContext.supportActionBar != null) {
                Log.d("isAppCompatActivity", "==AppCompatActivity")
                actionBarHeight = mContext.supportActionBar!!.height
            } else if (mContext is Activity && mContext.actionBar != null) {
                Log.d("isActivity", "==Activity")
                actionBarHeight = mContext.actionBar!!.height
            } else if (mContext is ActivityGroup) {
                Log.d("ActivityGroup", "==ActivityGroup")
                if (mContext.currentActivity is AppCompatActivity && (mContext.currentActivity as AppCompatActivity).supportActionBar != null) {
                    actionBarHeight = (mContext.currentActivity as AppCompatActivity).supportActionBar!!
                        .height
                } else if (mContext.currentActivity != null && (mContext.currentActivity as Activity).actionBar != null) {
                    actionBarHeight = (mContext.currentActivity as Activity).actionBar!!.height
                }
            }
            if (actionBarHeight != 0) {
                return actionBarHeight
            }
            val tv = TypedValue()
            if (mContext.theme
                    .resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)
            ) {
                if (mContext.theme
                        .resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)
                ) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data,
                        mContext.resources.displayMetrics
                    )
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (mContext.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data,
                        mContext.resources.displayMetrics
                    )
                }
            } else {
                if (mContext.theme
                        .resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)
                ) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data,
                        mContext.resources.displayMetrics
                    )
                }
            }
            Log.d("actionBarHeight", "====$actionBarHeight")
            return actionBarHeight
        }
    }
}
