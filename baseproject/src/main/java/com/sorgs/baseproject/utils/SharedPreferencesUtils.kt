package com.sorgs.baseproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.sorgs.baseproject.base.SorgsOptions

/**
 * 用户缓存
 *
 *
 * on 2019/4/8.
 *
 * @author sorgs
 */

class SharedPreferencesUtils private constructor() {
    private val sharedPreferences: SharedPreferences =
        SorgsOptions.mContext.getSharedPreferences("cache", Context.MODE_PRIVATE)

    /**
     * 写入String变量到sharedPreferences中
     *
     * @param key 存储节点名称
     * @param value 储存节点的值boolean
     */
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * 读取String表示从getSharedPreferences中
     *
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return 默认值或者节点读取到的结果
     */
    fun getString(key: String, defValue: String): String? {
        return sharedPreferences.getString(key, defValue)
    }

    /**
     * 写入boolean变量到sharedPreferences中
     *
     * @param key 存储节点名称
     * @param value 储存节点的值boolean
     */
    fun putBoolean(key: String, value: Boolean) {
        //储存节点文件名称，读写方式
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * 读取boolean表示从getSharedPreferences中
     *
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return 默认值或者节点读取到的结果
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        //存储节点文件名称，读写方式
        return sharedPreferences.getBoolean(key, defValue)
    }

    /**
     * 写入int变量到sharedPreferences中
     *
     * @param key 存储节点名称
     * @param value 储存节点的值int
     */
    fun putInt(key: String, value: Int) {
        //储存节点文件名称，读写方式
        sharedPreferences.edit().putInt(key, value).apply()
    }

    /**
     * 读取Int表示从getSharedPreferences中
     *
     * @param key 储存节点名称
     * @param defValue 没有此节点的默认值
     * @return 默认值或者节点读取到的结果
     */
    fun getint(key: String, defValue: Int): Int {
        //存储节点文件名称，读写方式
        return sharedPreferences.getInt(key, defValue)
    }

    /**
     * 删除sharedPreferences中的节点
     *
     * @param key 需要删除的节点
     */
    fun remove(key: String): Boolean {
        return sharedPreferences.edit().remove(key).commit()
    }

    companion object {
        val instance: SharedPreferencesUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SharedPreferencesUtils()
        }
    }
}
