package com.sorgs.baseproject.square;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author sorgs
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 正方形的 TextView
 */
public final class SquareTextView extends AppCompatTextView {

    public SquareTextView(Context context) {
        super(context);
    }

    public SquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(SquareDelegate.measureWidth(widthMeasureSpec, heightMeasureSpec),
                SquareDelegate.measureHeight(widthMeasureSpec, heightMeasureSpec));
    }
}