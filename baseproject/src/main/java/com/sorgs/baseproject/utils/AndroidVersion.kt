package com.sorgs.baseproject.utils

import android.os.Build

/**
 * 以更加可读的方式提供Android系统版本号的判断方法。
 *
 */
object AndroidVersion {

    /**
     * 判断当前手机系统版本API是否是16以上。
     * @return 16以上返回true，否则返回false。
     */
    fun hasJellyBean(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 判断当前手机系统版本API是否是16及其以下。
     * @return 16及其以下返回true，否则返回false。
     */
    fun hasNoJellyBean(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 判断当前手机系统版本API是否是17以上。
     * @return 17以上返回true，否则返回false。
     */
    fun hasJellyBeanMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    /**
     * 判断当前手机系统版本API是否是17及其以下。
     * @return 17及其以下返回true，否则返回false。
     */
    fun hasNoJellyBeanMR1(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    /**
     * 判断当前手机系统版本API是否是18以上。
     * @return 18以上返回true，否则返回false。
     */
    fun hasJellyBeanMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    /**
     * 判断当前手机系统版本API是否是18及其以下。
     * @return 18及其以下返回true，否则返回false。
     */
    fun hasNoJellyBeanMR2(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    /**
     * 判断当前手机系统版本API是否是19以上。
     * @return 19以上返回true，否则返回false。
     */
    fun hasKitkat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    /**
     * 判断当前手机系统版本API是否是19及其以下。
     * @return 19及其以下返回true，否则返回false。
     */
    fun hasNoKitkat(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT
    }

    /**
     * 判断当前手机系统版本API是否是21以上。
     * @return 21以上返回true，否则返回false。
     */
    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 判断当前手机系统版本API是否是21及其以下。
     * @return 21及其以下返回true，否则返回false。
     */
    fun hasNoLollipop(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 判断当前手机系统版本API是否是22以上。
     * @return 22以上返回true，否则返回false。
     */
    fun hasLollipopMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    /**
     * 判断当前手机系统版本API是否是22及其以下。
     * @return 22及其以下返回true，否则返回false。
     */
    fun hasNoLollipopMR1(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    /**
     * 判断当前手机系统版本API是否是23以上。
     * @return 23以上返回true，否则返回false。
     */
    fun hasMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * 判断当前手机系统版本API是否是23及其以下。
     * @return 23及其以下返回true，否则返回false。
     */
    fun hasNoMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.M
    }


    /**
     * 判断当前手机系统版本API是否是24以上。
     * @return 24以上返回true，否则返回false。
     */
    fun hasNougat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    /**
     * 判断当前手机系统版本API是否是24及其以下。
     * @return 24及其以下返回true，否则返回false。
     */
    fun hasNoNougat(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.N
    }


}