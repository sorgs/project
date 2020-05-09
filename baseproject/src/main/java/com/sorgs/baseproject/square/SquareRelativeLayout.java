package com.sorgs.baseproject.square;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author sorgs
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 正方形的 RelativeLayout
 */
public final class SquareRelativeLayout extends RelativeLayout {

    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(SquareDelegate.measureWidth(widthMeasureSpec, heightMeasureSpec),
                SquareDelegate.measureHeight(widthMeasureSpec, heightMeasureSpec));
    }
}