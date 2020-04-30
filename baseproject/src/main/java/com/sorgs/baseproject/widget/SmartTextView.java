package com.sorgs.baseproject.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author sorgs
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/08/18
 * desc   : 智能显示的 TextView
 */
public final class SmartTextView extends AppCompatTextView implements TextWatcher {

    public SmartTextView(Context context) {
        this(context, null);
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addTextChangedListener(this);
        // 触发一次监听
        afterTextChanged(null);
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 判断当前有没有设置文本达到自动隐藏和显示的效果
        if ("".equals(getText().toString())) {
            if (getVisibility() != GONE) {
                setVisibility(GONE);
            }
        } else {
            if (getVisibility() != VISIBLE) {
                setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}