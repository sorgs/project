package com.sorgs.baseproject.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sorgs.baseproject.R
import com.sorgs.baseproject.imageloader.transformations.BlurTransformation
import com.sorgs.baseproject.imageloader.transformations.CropCircleTransformation

/**
 * description: 利用Glide加载图片,不对外调用.
 *
 * @author Sorgs.
 */
internal object GlideImageLoader {

    /**
     * 图片加载。
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadImage(
        context: Context,
        url: Int,
        imageView: ImageView,
        holderResource: Int
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(holderResource)
            .error(holderResource)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(imageView)
    }

    /**
     * 图片加载。
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        holderResource: Int
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(holderResource)
            .error(holderResource)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(imageView)
    }


    /**
     * 圆形图片加载
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadCircleImage(
        context: Context?,
        url: String,
        imageView: ImageView,
        holderResource: Int
    ) {
        if (context != null) {
            Glide.with(context)
                .load(url)
                .bitmapTransform(CropCircleTransformation(context))
                .placeholder(R.drawable.loading_bg_circle)
                .error(holderResource)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView)
        }
    }

    /**
     * 圆形图片加载
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadCircleImage(
        context: Context?,
        url: Int,
        imageView: ImageView,
        holderResource: Int
    ) {
        if (context != null) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading_bg_circle)
                .error(holderResource)
                .bitmapTransform(CropCircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView)
        }
    }

    /**
     * 加载高斯模糊的图片
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param radius 模糊度
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: String,
        imageView: ImageView,
        radius: Int,
        holderResource: Int
    ) {
        if (context != null) {
            Glide.with(context)
                .load(url)
                .placeholder(holderResource)
                .error(holderResource)
                .bitmapTransform(BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imageView)
        }
    }

    /**
     * 加载高斯模糊的图片
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param radius 模糊度
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: Int,
        imageView: ImageView,
        radius: Int,
        holderResource: Int
    ) {
        if (context != null) {
            Glide.with(context)
                .load(url)
                .placeholder(holderResource)
                .error(holderResource)
                .bitmapTransform(BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imageView)
        }

    }

    /**
     * 清除图片缓存
     */
    fun clearDiskCache(context: Context) {
        Glide.get(context)
            .clearDiskCache()
    }

    /**
     * 清除内存
     */
    fun clearMemory(context: Context) {
        Glide.get(context)
            .clearMemory()
    }
}
