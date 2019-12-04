package com.sorgs.baseproject.imageloader.bumptech.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.Encoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.Key;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.ResourceDecoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.ResourceEncoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.Transformation;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ImageVideoWrapper;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapperTransformation;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.manager.Lifecycle;
import com.sorgs.baseproject.imageloader.bumptech.glide.manager.RequestTracker;
import com.sorgs.baseproject.imageloader.bumptech.glide.provider.LoadProvider;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.RequestListener;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * A class for creating a request to load a {@link GlideDrawable}.
 *
 * <p>
 * Warning - It is <em>not</em> safe to use this builder after calling <code>into()</code>, it may be pooled and
 * reused.
 * </p>
 *
 * @param <ModelType> The type of model that will be loaded into the target.
 */
public class DrawableRequestBuilder<ModelType>
        extends GenericRequestBuilder<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable>
        implements BitmapOptions, DrawableOptions {

    DrawableRequestBuilder(Context context, Class<ModelType> modelClass,
                           LoadProvider<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable> loadProvider,
                           Glide glide,
                           RequestTracker requestTracker, Lifecycle lifecycle) {
        super(context, modelClass, loadProvider, GlideDrawable.class, glide, requestTracker, lifecycle);
        // Default to animating.
        crossFade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DrawableRequestBuilder<ModelType> crossFade() {
        super.animate(new DrawableCrossFadeFactory<GlideDrawable>());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> crossFade(int duration) {
        super.animate(new DrawableCrossFadeFactory<GlideDrawable>(duration));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public DrawableRequestBuilder<ModelType> crossFade(Animation animation, int duration) {
        super.animate(new DrawableCrossFadeFactory<GlideDrawable>(animation, duration));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> crossFade(int animationId, int duration) {
        super.animate(new DrawableCrossFadeFactory<GlideDrawable>(context, animationId,
                duration));
        return this;
    }

    /**
     * Loads and displays the {@link GlideDrawable} retrieved by the given thumbnail request if it finishes before this
     * request. Best used for loading thumbnail {@link GlideDrawable}s that are smaller and will be loaded more quickly
     * than the fullsize {@link GlideDrawable}. There are no guarantees about the order in which the requests will
     * actually finish. However, if the thumb request completes after the full request, the thumb {@link GlideDrawable}
     * will never replace the full image.
     *
     * @param thumbnailRequest The request to use to load the thumbnail.
     * @return This builder object.
     *
     * @see #thumbnail(float)
     *
     * <p>
     * Note - Any options on the main request will not be passed on to the thumbnail request. For example, if
     * you want an animation to occur when either the full {@link GlideDrawable} loads or the thumbnail loads,
     * you need to call {@link #animate(int)} on both the thumb and the full request. For a simpler thumbnail
     * option where these options are applied to the humbnail as well, see {@link #thumbnail(float)}.
     * </p>
     *
     * <p>
     * Only the thumbnail call on the main request will be obeyed, recursive calls to this method are ignored.
     * </p>
     */
    public DrawableRequestBuilder<ModelType> thumbnail(
            DrawableRequestBuilder<?> thumbnailRequest) {
        super.thumbnail(thumbnailRequest);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> thumbnail(
            GenericRequestBuilder<?, ?, ?, GlideDrawable> thumbnailRequest) {
        super.thumbnail(thumbnailRequest);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> thumbnail(float sizeMultiplier) {
        super.thumbnail(sizeMultiplier);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> sizeMultiplier(float sizeMultiplier) {
        super.sizeMultiplier(sizeMultiplier);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> decoder(ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> decoder) {
        super.decoder(decoder);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> cacheDecoder(ResourceDecoder<File, GifBitmapWrapper> cacheDecoder) {
        super.cacheDecoder(cacheDecoder);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> sourceEncoder(Encoder<ImageVideoWrapper> sourceEncoder) {
        super.sourceEncoder(sourceEncoder);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
        super.diskCacheStrategy(strategy);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> encoder(ResourceEncoder<GifBitmapWrapper> encoder) {
        super.encoder(encoder);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> priority(Priority priority) {
        super.priority(priority);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see #bitmapTransform(Transformation[])
     * @see #centerCrop()
     * @see #fitCenter()
     */
    @Override
    public DrawableRequestBuilder<ModelType> transform(Transformation<GifBitmapWrapper>... transformation) {
        super.transform(transformation);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> dontTransform() {
        super.dontTransform();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> transcoder(
            ResourceTranscoder<GifBitmapWrapper, GlideDrawable> transcoder) {
        super.transcoder(transcoder);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> dontAnimate() {
        super.dontAnimate();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> animate(int animationId) {
        super.animate(animationId);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    @Override
    public DrawableRequestBuilder<ModelType> animate(Animation animation) {
        super.animate(animation);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> animate(ViewPropertyAnimation.Animator animator) {
        super.animate(animator);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> placeholder(int resourceId) {
        super.placeholder(resourceId);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> placeholder(Drawable drawable) {
        super.placeholder(drawable);
        return this;
    }

    @Override
    public DrawableRequestBuilder<ModelType> fallback(Drawable drawable) {
        super.fallback(drawable);
        return this;
    }

    @Override
    public DrawableRequestBuilder<ModelType> fallback(int resourceId) {
        super.fallback(resourceId);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> error(int resourceId) {
        super.error(resourceId);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> error(Drawable drawable) {
        super.error(drawable);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> listener(
            RequestListener<? super ModelType, GlideDrawable> requestListener) {
        super.listener(requestListener);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> skipMemoryCache(boolean skip) {
        super.skipMemoryCache(skip);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRequestBuilder<ModelType> override(int width, int height) {
        super.override(width, height);
        return this;
    }

    @Override
    public DrawableRequestBuilder<ModelType> signature(Key signature) {
        super.signature(signature);
        return this;
    }

    @Override
    public DrawableRequestBuilder<ModelType> load(ModelType model) {
        super.load(model);
        return this;
    }

    @Override
    public DrawableRequestBuilder<ModelType> clone() {
        return (DrawableRequestBuilder<ModelType>) super.clone();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Note - If no transformation is set for this load, a default transformation will be applied based on the
     * value returned from {@link ImageView#getScaleType()}. To avoid this default transformation,
     * use {@link #dontTransform()}.
     * </p>
     *
     * @param view {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Target<GlideDrawable> into(ImageView view) {
        return super.into(view);
    }

    @Override
    void applyCenterCrop() {
        centerCrop();
    }

    @Override
    void applyFitCenter() {
        fitCenter();
    }

    /**
     * Transform {@link GlideDrawable}s using
     * {@link com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap.FitCenter}.
     *
     * @return This request builder.
     *
     * @see #centerCrop()
     * @see #transform(BitmapTransformation...)
     * @see #bitmapTransform(Transformation[])
     * @see #transform(Transformation[])
     */
    @SuppressWarnings("unchecked")
    public DrawableRequestBuilder<ModelType> fitCenter() {
        return transform(glide.getDrawableFitCenter());
    }

    /**
     * Transform {@link GlideDrawable}s using
     * {@link com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap.CenterCrop}.
     *
     * @return This request builder.
     *
     * @see #fitCenter()
     * @see #transform(BitmapTransformation...)
     * @see #bitmapTransform(Transformation[])
     * @see #transform(Transformation[])
     */
    @SuppressWarnings("unchecked")
    public DrawableRequestBuilder<ModelType> centerCrop() {
        return transform(glide.getDrawableCenterCrop());
    }

    /**
     * Transform {@link GlideDrawable}s using the given
     * {@link BitmapTransformation}s.
     *
     * <p>
     * Note - Bitmap transformations will apply individually to each frame of animated GIF images and also to
     * individual {@link Bitmap}s.
     * </p>
     *
     * @param transformations The transformations to apply in order.
     * @return This request builder.
     *
     * @see #centerCrop()
     * @see #fitCenter()
     * @see #bitmapTransform(Transformation[])
     * @see #transform(Transformation[])
     */
    public DrawableRequestBuilder<ModelType> transform(BitmapTransformation... transformations) {
        return bitmapTransform(transformations);
    }

    /**
     * Transform {@link GlideDrawable}s using the given {@link Bitmap} transformations. Replaces any
     * previous transformations.
     *
     * @return This request builder.
     *
     * @see #fitCenter()
     * @see #centerCrop()
     * @see #transform(BitmapTransformation...)
     * @see #transform(Transformation[])
     */
    public DrawableRequestBuilder<ModelType> bitmapTransform(Transformation<Bitmap>... bitmapTransformations) {
        GifBitmapWrapperTransformation[] transformations =
                new GifBitmapWrapperTransformation[bitmapTransformations.length];
        for (int i = 0; i < bitmapTransformations.length; i++) {
            transformations[i] = new GifBitmapWrapperTransformation(glide.getBitmapPool(), bitmapTransformations[i]);
        }
        return transform(transformations);
    }
}
