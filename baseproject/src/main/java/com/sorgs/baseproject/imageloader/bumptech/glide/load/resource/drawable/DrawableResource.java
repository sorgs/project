package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.Resource;

/**
 * Simple wrapper for an Android {@link Drawable} which returns a
 * {@link Drawable.ConstantState#newDrawable() new drawable}
 * based on it's {@link Drawable.ConstantState state}.
 *
 * <b>Suggested usages only include {@code T}s where the new drawable is of the same or descendant class.</b>
 *
 * @param <T> type of the wrapped {@link Drawable}
 */
public abstract class DrawableResource<T extends Drawable> implements Resource<T> {
    protected final T drawable;

    public DrawableResource(T drawable) {
        if (drawable == null) {
            throw new NullPointerException("Drawable must not be null!");
        }
        this.drawable = drawable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final T get() {
        // Drawables contain temporary state related to how they're being displayed (alpha, color filter etc), so
        // return a new copy each time. If we ever return the original drawable, it's temporary state may be changed
        // and subsequent copies may end up with that temporary state. See #276.
        return (T) drawable.getConstantState().newDrawable();
    }
}
