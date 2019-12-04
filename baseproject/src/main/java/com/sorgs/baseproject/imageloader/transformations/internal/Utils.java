package com.sorgs.baseproject.imageloader.transformations.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.sorgs.baseproject.utils.AndroidVersion;


public final class Utils {

    private Utils() {
        // Utility class.
    }

    public static Drawable getMaskDrawable(Context context, int maskId) {
        Drawable drawable;
        if (AndroidVersion.INSTANCE.hasLollipop()) {
            drawable = context.getDrawable(maskId);
        } else {
            drawable = context.getResources().getDrawable(maskId);
        }

        if (drawable == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }

        return drawable;
    }
}
