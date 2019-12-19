package com.sorgs.baseproject.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * description: 图片加载器，对外暴露接口.
 *
 * @author Sorgs.
 */
object PicLoader {

    /**
     * 图片加载
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
        GlideImageLoader.loadImage(context, url, imageView, holderResource)
    }

    /**
     * 图片加载
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
        GlideImageLoader.loadImage(context, url, imageView, holderResource)
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
        GlideImageLoader.loadCircleImage(context, url, imageView, holderResource)
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
        GlideImageLoader.loadCircleImage(context, url, imageView, holderResource)
    }

    /**
     * 加载模糊度为50的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: String,
        imageView: ImageView,
        holderResource: Int
    ) {
        GlideImageLoader.loadBlurImage(context, url, imageView, 50, holderResource)
    }

    /**
     * 加载模糊度为50的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: Int,
        imageView: ImageView,
        holderResource: Int
    ) {
        GlideImageLoader.loadBlurImage(context, url, imageView, 50, holderResource)
    }

    /**
     * 加载模糊度为自定义的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param ambiguity 模糊度
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: Int,
        imageView: ImageView,
        ambiguity: Int,
        holderResource: Int
    ) {
        GlideImageLoader.loadBlurImage(context, url, imageView, ambiguity, holderResource)
    }

    /**
     * 加载模糊度为自定义的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param ambiguity 模糊度
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadBlurImage(
        context: Context?,
        url: String,
        imageView: ImageView,
        ambiguity: Int,
        holderResource: Int
    ) {
        GlideImageLoader.loadBlurImage(context, url, imageView, ambiguity, holderResource)
    }

    /**
     * 清除图片文件缓存
     */
    fun clearDiskCache(context: Context) {
        GlideImageLoader.clearDiskCache(context)
    }

    /**
     * 清除图片占用的无用内存
     */
    fun clearMemory(context: Context) {
        GlideImageLoader.clearMemory(context)
    }
}
