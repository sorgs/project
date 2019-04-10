package com.sorgs.baseproject.utils.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * description: 图片加载器，对外暴露接口.
 *
 * @author Sorgs.
 */
object PicLoader {

    /**
     * 带有渐入式效果的图片加载。
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadCrossFadeImage(
        context: Context,
        url: Int,
        imageView: ImageView,
        holderResource: Int
    ) {
        GlideImageLoader.loadCrossFadeImage(context, url, imageView, holderResource)
    }

    /**
     * 带有渐入式效果的图片加载。
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     * @param holderResource 错误显示的图片和占位图资源ID
     */
    fun loadCrossFadeImage(
        context: Context,
        url: String,
        imageView: ImageView,
        holderResource: Int
    ) {
        GlideImageLoader.loadCrossFadeImage(context, url, imageView, holderResource)
    }

    /**
     * 简单图片加载
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     */
    fun loadImage(
        context: Context,
        url: Int,
        imageView: ImageView
    ) {
        GlideImageLoader.loadImage(context, url, imageView)
    }

    /**
     * 简单图片加载
     *
     * @param context        上下文
     * @param url            图片地址
     * @param imageView      图片显示view
     */
    fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        GlideImageLoader.loadImage(context, url, imageView)
    }

    /**
     * 带有渐入效果的circleCrop圆形图片加载
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     */
    fun loadCircleCropCrossFadImage(
        context: Context?,
        url: String,
        imageView: ImageView
    ) {
        GlideImageLoader.loadCircleCropCrossFadImage(context, url, imageView)
    }

    /**
     * 带有渐入效果的circleCrop圆形图片加载
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     */
    fun loadCircleCropCrossFadImage(
        context: Context?,
        url: Int,
        imageView: ImageView
    ) {
        GlideImageLoader.loadCircleCropCrossFadImage(context, url, imageView)
    }

    /**
     * 加载默认模糊度为50的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     */
    fun loadImageBlur(
        context: Context?,
        url: String,
        imageView: ImageView
    ) {
        GlideImageLoader.loadRenderScriptImage(context, url, imageView, 50)
    }

    /**
     * 加载默认模糊度为50的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     */
    fun loadImageBlur(
        context: Context?,
        url: Int,
        imageView: ImageView
    ) {
        GlideImageLoader.loadRenderScriptImage(context, url, imageView, 50)
    }

    /**
     * 加载默认模糊度为自定义的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param ambiguity 模糊度
     */
    fun loadImageBlur(
        context: Context?,
        url: Int,
        imageView: ImageView,
        ambiguity: Int
    ) {
        GlideImageLoader.loadRenderScriptImage(context, url, imageView, ambiguity)
    }

    /**
     * 加载默认模糊度为自定义的高斯模糊图
     *
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片显示view
     * @param ambiguity 模糊度
     */
    fun loadImageBlur(
        context: Context?,
        url: String,
        imageView: ImageView,
        ambiguity: Int
    ) {
        GlideImageLoader.loadRenderScriptImage(context, url, imageView, ambiguity)
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

    /**
     * 清除View上的图片
     *
     * @param context   上下文
     * @param imageView 图片显示view
     */
    fun clearImageView(
        context: Context,
        imageView: ImageView
    ) {
        GlideImageLoader.clearImageView(context, imageView)
    }
}
