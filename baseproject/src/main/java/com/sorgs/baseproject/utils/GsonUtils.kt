package com.sorgs.baseproject.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * description: json工具类.
 *
 * @author Sorgs.
 * Created date: 2019/5/31.
 */
object GsonUtils {
    /**
     * 不用创建对象,直接使用Gson.就可以调用方法
     */
    private var gson: Gson? = null

    //判断gson对象是否存在了,不存在则创建对象
    init {
        if (gson == null) {
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        }
    }

    /**
     * 将对象转成json格式
     *
     * @param any
     * @return String
     */
    fun GsonString(any: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson!!.toJson(any)
        }
        return gsonString
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToBean(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToList(gsonString: String, cls: Class<T>): List<T>? {
        var list: List<T>? = null
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<List<T>>() {

            }.type)
        }
        return list
    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToListMaps(gsonString: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<Map<String, T>>>(
                gsonString,
                object : TypeToken<List<Map<String, T>>>() {

                }.type
            )
        }
        return list
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            map = gson!!.fromJson<Map<String, T>>(gsonString, object : TypeToken<Map<String, T>>() {

            }.type)
        }
        return map
    }
}
