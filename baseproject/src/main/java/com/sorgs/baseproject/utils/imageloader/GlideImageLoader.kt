package com.sorgs.baseproject.utils.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * description: 利用Glide加载图片,不对外调用.
 *
 * @author Sorgs.
 */
internal object GlideImageLoader {

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
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(holderResource)
                .error(holderResource)
                .optionalCenterCrop()
        )
        .transition(DrawableTransitionOptions().crossFade())
        .into(imageView)
  }

  /**
   * 图片加载。
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
    Glide.with(context)
        .load(url)
        .into(imageView)
  }

  /**
   * 图片加载。
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
    Glide.with(context)
        .load(url)
        .into(imageView)
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
    if (context != null) {
      Glide.with(context)
          .load(url)
          .apply(
              RequestOptions()
                  .optionalFitCenter()
                  .optionalCircleCrop()
          )
          .transition(DrawableTransitionOptions().crossFade())
          .into(imageView)
    }
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
    if (context != null) {
      Glide.with(context)
          .load(url)
          .apply(
              RequestOptions()
                  .optionalFitCenter()
                  .optionalCircleCrop()
          )
          .transition(DrawableTransitionOptions().crossFade())
          .into(imageView)
    }
  }

  /**
   * 系统方式加载高斯模糊的图片
   *
   * @param context 上下文
   * @param url 图片地址
   * @param imageView 图片显示view
   * @param radius 模糊度
   */
  fun loadRenderScriptImage(
    context: Context?,
    url: String,
    imageView: ImageView,
    radius: Int
  ) {
    if (context != null) {
      Glide.with(context)
          .asBitmap()
          .load(url)
          .apply(
              RequestOptions()
                  .centerCrop()
                  .transform(BlurTransformation(radius))
                  .dontAnimate()
          )
          .into(imageView)
    }

  }

  /**
   * 系统方式加载高斯模糊的图片
   *
   * @param context 上下文
   * @param url 图片地址
   * @param imageView 图片显示view
   * @param radius 模糊度
   */
  fun loadRenderScriptImage(
    context: Context?,
    url: Int,
    imageView: ImageView,
    radius: Int
  ) {
    if (context != null) {
      Glide.with(context)
          .asBitmap()
          .load(url)
          .apply(
              RequestOptions()
                  .centerCrop()
                  .transform(BlurTransformation(radius))
                  .dontAnimate()
          )
          .into(imageView)
    }

  }

  /**
   * 清除图片
   *
   * @param context   上下文
   * @param imageView 图片显示view
   */
  fun clearImageView(
    context: Context,
    imageView: ImageView
  ) {
    Glide.with(context)
        .clear(imageView)
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
