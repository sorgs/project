package com.sorgs.baseproject.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * description: 按下效果工具类.
 */
public final class PressEffectHelper {

    /**
     * 半透明效果,自己写过的touch事件会失效
     */
    public static void alphaHalfEffect(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.5f);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1f);
                    break;
                default:
                    break;
            }
            return false;
        });
    }
}
