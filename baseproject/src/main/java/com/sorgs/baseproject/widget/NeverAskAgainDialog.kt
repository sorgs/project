package com.sorgs.baseproject.widget

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog

/**
 * description: xxx.
 *
 * @author Sorgs.
 * @date 2018/2/23.
 */

object NeverAskAgainDialog {

    /**
     * 弹出跳转设置dialog
     */
    fun goToSettingDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setMessage("您好，我们需要您开启权限申请，才能方便使用！：\n请点击前往设置页面\n")
            .setPositiveButton("前往设置页面") { _, _ -> gotoSettings(activity) }
            .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * 跳转设置页面
     */
    fun gotoSettings(activity: Activity) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }
}
