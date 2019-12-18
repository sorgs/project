package com.sorgs.baseproject.utils

import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * description: 文件操作类.

 * @author Sorgs.
 * Created date: 2019/12/18.
 */
object FileUtils {
    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    fun copy(source: File?, target: File?) {
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 创建文件
     *
     * @param dir 文件夹
     * @param fileName 文件名称
     */
    fun mkFile(dir: String?, fileName: String?): File {
        if (TextUtils.isEmpty(dir)) {
            LogUtils.e("dir is null!!!")
        }
        if (TextUtils.isEmpty(fileName)) {
            LogUtils.e("name is null!!!")
        }
        //根目录
        val rootPath = Environment.getExternalStorageDirectory()
        //第二个参数为你想要保存的目录名称
        val appDir = File(rootPath, dir)
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        return File(appDir, fileName)
    }
}