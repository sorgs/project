package com.sorgs.baseproject.utils.imageloader

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import java.security.MessageDigest

/**
 * description : Glide获取灰度图片的处理类
 *
 * @author : YangYang
 * @date : 2018/6/8 16:32
 */
class GrayscaleTransformation : BitmapTransformation() {

    override fun transform(
        context: Context,
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val config = if (toTransform.config != null) toTransform.config else Bitmap.Config.ARGB_8888
        val bitmap = pool.get(width, height, config)

        val canvas = Canvas(bitmap)
        val saturation = ColorMatrix()
        saturation.setSaturation(0f)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(saturation)
        canvas.drawBitmap(toTransform, 0f, 0f, paint)

        return bitmap
    }

    override fun toString(): String {
        return "GrayscaleTransformation()"
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
        private val ID =
            "com.aimymusic.android.base.http.imageloader.glide.GrayscaleTransformation.$VERSION"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }
}
