package com.sorgs.baseproject.imageloader.bumptech.glide;

import android.view.animation.Animation;

interface DrawableOptions {

    /**
     * Applies a cross fade transformation that fades from the placeholder to the loaded
     * {@link android.graphics.drawable.Drawable}. If no placeholder is set, the Drawable will instead simply fade in.
     *
     * @return This request builder.
     *
     * @see #crossFade(int)
     * @see #crossFade(int, int)
     */
    GenericRequestBuilder<?, ?, ?, ?> crossFade();

    /**
     * Applies a cross fade transformation that fades from the placeholder to the loaded
     * {@link android.graphics.drawable.Drawable}. If no placeholder is set the Drawable will instead simply fade in.
     *
     * @param duration The duration of the cross fade and initial fade in.
     * @return This request builder.
     *
     * @see #crossFade()
     * @see #crossFade(int, int)
     */
    GenericRequestBuilder<?, ?, ?, ?> crossFade(int duration);


    /**
     * Applies a cross fade transformation that des from the placeholder to the loaded
     * {@link android.graphics.drawable.Drawable}. If no placeholder is set, the Drawable will instead be animated in
     * using the given {@link Animation}.
     *
     * @param animation The Animation to use if no placeholder is set.
     * @param duration  The duration of the cross fade animation.
     * @return This request builder.
     *
     * @see #crossFade()
     * @see #crossFade(int)
     * @see #crossFade(int, int)
     * @deprecated If this builder is used for multiple loads, using this method will result in multiple view's being
     * asked to start an animation using a single {@link Animation} object which results in
     * views animating repeatedly. Use {@link #crossFade(int, int)}} instead, or be sure to call this method once
     * per call to {@link GenericRequestBuilder#load(Object)} to avoid re-using animation objects.
     * Scheduled to be removed in Glide 4.0.
     */
    @Deprecated
    GenericRequestBuilder<?, ?, ?, ?> crossFade(Animation animation, int duration);

    /**
     * Applies a cross fade transformation that des from the placeholder to the loaded
     * {@link android.graphics.drawable.Drawable}. If no placeholder is set, the Drawable will instead be animated in
     * using the {@link Animation} loaded from the given animation id.
     *
     * @param animationId The id of the Animation to use if no placeholder is set.
     * @param duration    The duration of the cross fade animation.
     * @return This request builder.
     *
     * @see #crossFade()
     * @see #crossFade(int)
     */
    GenericRequestBuilder<?, ?, ?, ?> crossFade(int animationId, int duration);
}
