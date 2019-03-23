package com.sorgs.baseproject.utils.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.Key

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool

import java.security.MessageDigest

/**
 * description :高斯模糊的转换类
 *
 * @author : YangYang
 * @date : 2018/6/8 18:06
 */
class BlurTransformation(private var radius: Int) : BitmapTransformation() {

  override fun transform(
    context: Context,
    pool: BitmapPool,
    toTransform: Bitmap,
    outWidth: Int,
    outHeight: Int
  ): Bitmap {
    //return nativeBlur(pool, toTransform, outWidth, outHeight);
    return renderScriptBlur(context, toTransform, outWidth)
  }

  /**
   * 用大神用c++中处理的native方式实现高斯模糊
   */
  //private Bitmap nativeBlur(@NonNull BitmapPool pool,
  //    @NonNull Bitmap toTransform, int outWidth, int outHeight) {
  //  float scale = 1f;
  //  if (outWidth > 77) {
  //    scale = 77f / outWidth;
  //  }
  //  if (scale != 1.0) {
  //    outWidth = (int) (toTransform.getWidth() * scale) / 2 * 2;
  //    outHeight = (int) (toTransform.getHeight() * scale) / 2 * 2;
  //  }
  //  ByteBuffer image_output =
  //      VideoEditingCore.makeBlurImage(toTransform, outWidth, outHeight, radius);
  //  Bitmap output = pool.get(outWidth, outHeight, ARGB_8888);
  //  output.copyPixelsFromBuffer(image_output);
  //  return output;
  //}

  /**
   * 用系统的RenderScript+缩小的方式实现高斯模糊
   */
  private fun renderScriptBlur(
    context: Context,
    source: Bitmap,
    outWidth: Int
  ): Bitmap {

    if (radius > 25) {
      radius = 25
    }

    var scale = 1f
    if (outWidth > 77) {
      scale = 77f / outWidth
    }

    val width = Math.round(source.width * scale)
    val height = Math.round(source.height * scale)

    val inputBmp = Bitmap.createScaledBitmap(source, width, height, false)

    val renderScript = RenderScript.create(context)

    // Allocate memory for Renderscript to work with

    val input = Allocation.createFromBitmap(renderScript, inputBmp)
    val output = Allocation.createTyped(renderScript, input.type)

    // Load up an instance of the specific script that we want to use.
    val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    scriptIntrinsicBlur.setInput(input)

    // Set the blur radius
    scriptIntrinsicBlur.setRadius(radius.toFloat())

    // Start the ScriptIntrinisicBlur
    scriptIntrinsicBlur.forEach(output)

    // Copy the output to the blurred bitmap
    output.copyTo(inputBmp)

    renderScript.destroy()

    return inputBmp
  }

  override fun toString(): String {
    return "BlurTransformation"
  }

  override fun equals(other: Any?): Boolean {
    return other is GrayscaleTransformation
  }

  override fun hashCode(): Int {
    return ID.hashCode()
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    messageDigest.update(ID_BYTES)
  }

  companion object {
    private val VERSION = 1
    private val ID = "com.aimymusic.android.base.http.imageloader.glide.BlurTransformation.$VERSION"
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)
  }
}
