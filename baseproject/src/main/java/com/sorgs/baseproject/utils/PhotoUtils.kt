package com.sorgs.baseproject.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.FileProvider
import com.sorgs.baseproject.R
import com.sorgs.baseproject.base.BaseActivity
import com.sorgs.baseproject.base.BaseFragment
import com.sorgs.baseproject.widget.NeverAskAgainDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import rx.functions.Action1
import java.io.File
import java.io.IOException
import java.util.Objects

/**
 * description: 用于拍照、照片榴莲上传.
 *
 * @author Sorgs.
 */
object PhotoUtils {

  val PHOTO_SUCCESS = 2083
  val CAMERA_SUCCESS = 2082

  /**
   * activity 拍照
   */
  fun photo(
    baseActivity: BaseActivity,
    onNext: Action1<Uri>
  ) {
    photo(baseActivity, onNext, CAMERA_SUCCESS)
  }

  @SuppressLint("CheckResult")
  private fun photo(
    baseActivity: BaseActivity?,
    onNext: Action1<Uri>?,
    cameraSuccess: Int
  ) {
    if (baseActivity != null) {
      val rxPermissions = RxPermissions(baseActivity)
      rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe { permission ->
            when {
              permission.granted -> {
                // 用户已经同意该权限
                photo(rxPermissions, baseActivity, onNext, CAMERA_SUCCESS)
                val uri = goCamera(baseActivity, cameraSuccess)
                onNext?.call(uri)
              }
              permission.shouldShowRequestPermissionRationale -> // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                ToastUtils.showShort(R.string.toast_no_permission)
              else -> // 用户拒绝了该权限，并且选中『不再询问』
                NeverAskAgainDialog.goToSettingDialog(baseActivity)
            }
          }
    }
  }

  @SuppressLint("CheckResult")
  private fun photo(
    rxPermissions: RxPermissions,
    baseActivity: BaseActivity?,
    onNext: Action1<Uri>?,
    cameraSuccess: Int
  ) {
    rxPermissions.requestEach(Manifest.permission.CAMERA)
        .subscribe { permission ->
          if (permission.granted) {
            // 用户已经同意该权限
            photo(rxPermissions, baseActivity, onNext, CAMERA_SUCCESS)
            val uri = goCamera(baseActivity, cameraSuccess)
            onNext?.call(uri)
          } else if (permission.shouldShowRequestPermissionRationale) {
            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
            ToastUtils.showShort(R.string.toast_no_permission)
          } else {
            // 用户拒绝了该权限，并且选中『不再询问』
            NeverAskAgainDialog.goToSettingDialog(baseActivity!!)
          }
        }
  }

  private fun goCamera(
    context: Activity?,
    cameraSuccess: Int
  ): Uri {
    return if (Build.VERSION.SDK_INT >= 24) {
      goCameraAfter24(context!!, CAMERA_SUCCESS)
    } else {
      goCameraBefore24(context!!, CAMERA_SUCCESS)
    }
  }

  /**
   * 拍照
   * sdk大于24，需要用FileProvider
   */
  private fun goCameraAfter24(
    context: Activity,
    cameraSuccess: Int
  ): Uri {
    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DCIM),
        System.currentTimeMillis().toString() + ".jpg"
    )
    try {
      if (file.exists()) {
        file.delete()
      }
      file.createNewFile()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    val outputFileUri = Uri.fromFile(file)
    val outputContentUri = FileProvider.getUriForFile(context, context.packageName, file)

    val intent = Intent()
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputContentUri)
    context.startActivityForResult(intent, CAMERA_SUCCESS)
    return outputFileUri
  }

  /**
   * 拍照
   */
  private fun goCameraBefore24(
    context: Activity,
    cameraSuccess: Int
  ): Uri {
    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DCIM),
        System.currentTimeMillis().toString() + ".jpg"
    )
    try {
      if (file.exists()) {
        file.delete()
      }
      file.createNewFile()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    val outputFileUri = Uri.fromFile(file)
    val intent = Intent()
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
    context.startActivityForResult(intent, CAMERA_SUCCESS)
    return outputFileUri
  }

  /**
   * fragment 拍照
   */
  fun photo(
    fragment: BaseFragment,
    onNext: Action1<Uri>
  ) {
    photo(fragment, onNext, CAMERA_SUCCESS)
  }

  @SuppressLint("CheckResult")
  fun photo(
    fragment: BaseFragment?,
    onNext: Action1<Uri>,
    cameraSuccess: Int
  ) {
    if (fragment != null) {
      val rxPermissions = RxPermissions(Objects.requireNonNull<FragmentActivity>(fragment.activity))
      rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe { permission ->
            if (permission.granted) {
              // 用户已经同意该权限
              photo(rxPermissions, fragment, onNext, cameraSuccess)
            } else if (permission.shouldShowRequestPermissionRationale) {
              // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
              ToastUtils.showShort(R.string.toast_no_permission)
            } else {
              // 用户拒绝了该权限，并且选中『不再询问』
              NeverAskAgainDialog.goToSettingDialog(fragment.activity!!)
            }
          }
    }
  }

  @SuppressLint("CheckResult")
  private fun photo(
    rxPermissions: RxPermissions,
    fragment: BaseFragment?,
    onNext: Action1<Uri>?,
    cameraSuccess: Int
  ) {
    if (fragment != null) {
      rxPermissions.request(Manifest.permission.CAMERA)
          .subscribe { permission ->
            if (permission!!) {
              val uri = goCamera(fragment, cameraSuccess)
              onNext?.call(uri)
            } else {
              ToastUtils.showShort(R.string.toast_no_permission)
            }
          }
    }
  }

  private fun goCamera(
    fragment: Fragment?,
    cameraSuccess: Int
  ): Uri {
    return if (Build.VERSION.SDK_INT >= 24) {
      goCameraAfter24(fragment!!, CAMERA_SUCCESS)
    } else {
      goCameraBefore24(fragment!!, CAMERA_SUCCESS)
    }
  }

  /**
   * 拍照
   * sdk大于24，需要用FileProvider
   */
  private fun goCameraAfter24(
    fragment: Fragment,
    cameraSuccess: Int
  ): Uri {
    val file = File(
        fragment.activity!!.getExternalFilesDir(Environment.DIRECTORY_DCIM),
        System.currentTimeMillis().toString() + ".jpg"
    )
    try {
      if (file.exists()) {
        file.delete()
      }
      file.createNewFile()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    val outputFileUri = Uri.fromFile(file)
    val outputContentUri =
      FileProvider.getUriForFile(fragment.activity!!, fragment.activity!!.packageName, file)

    val intent = Intent()
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputContentUri)
    fragment.activity!!.startActivityForResult(intent, cameraSuccess)
    return outputFileUri
  }

  /**
   * 拍照
   */
  private fun goCameraBefore24(
    fragment: Fragment,
    cameraSuccess: Int
  ): Uri {
    val file = File(
        fragment.activity!!.getExternalFilesDir(Environment.DIRECTORY_DCIM),
        System.currentTimeMillis().toString() + ".jpg"
    )
    try {
      if (file.exists()) {
        file.delete()
      }
      file.createNewFile()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    val outputFileUri = Uri.fromFile(file)
    val intent = Intent()
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
    fragment.activity!!.startActivityForResult(intent, cameraSuccess)
    return outputFileUri
  }

  /**
   * 相册选择
   */
  @SuppressLint("CheckResult", "IntentReset")
  fun gallery(
    baseActivity: BaseActivity?,
    photoSuccess: Int
  ) {
    if (baseActivity != null) {
      val rxPermissions = RxPermissions(baseActivity)
      rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe { permission ->
            if (permission.granted) {
              // 用户已经同意该权限
              var intent = Intent(Intent.ACTION_GET_CONTENT)
              if (Build.VERSION.SDK_INT < 19) {
                //图库选择
                intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)

              } else {
                intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                intent.type = "image/*"
              }
              if (intent.resolveActivity(baseActivity.packageManager) != null) {
                baseActivity.startActivityForResult(intent, photoSuccess)
              }
            } else if (permission.shouldShowRequestPermissionRationale) {
              // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
              ToastUtils.showShort(R.string.toast_no_permission)
            } else {
              // 用户拒绝了该权限，并且选中『不再询问』
              NeverAskAgainDialog.goToSettingDialog(baseActivity)
            }
          }

    }

  }

  /**
   * 相册选择
   */
  @SuppressLint("CheckResult", "IntentReset")
  fun gallery(
    baseFragment: BaseFragment?,
    photoSuccess: Int
  ) {
    if (baseFragment != null) {
      val rxPermissions = RxPermissions(baseFragment.activity!!)
      rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe { permission ->
            if (permission.granted) {
              // 用户已经同意该权限
              var intent = Intent(Intent.ACTION_GET_CONTENT)
              if (Build.VERSION.SDK_INT < 19) {
                //图库选择
                intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
              } else {
                intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                intent.type = "image/*"
              }
              if (intent.resolveActivity(baseFragment.activity!!.packageManager) != null) {
                baseFragment.startActivityForResult(intent, photoSuccess)
              }
            } else if (permission.shouldShowRequestPermissionRationale) {
              // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
              ToastUtils.showShort(R.string.toast_no_permission)
            } else {
              // 用户拒绝了该权限，并且选中『不再询问』
              NeverAskAgainDialog.goToSettingDialog(baseFragment.activity!!)
            }
          }
    }
  }

  fun getPath(
    context: Activity,
    uri: Uri?
  ): String {
    if (uri != null && uri.scheme == ContentResolver.SCHEME_FILE) {
      return uri.path!!.toString()
    }
    var filePath = ""
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri!!, filePathColumn, null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
      val columnIndex = cursor.getColumnIndex(filePathColumn[0])
      if (columnIndex > -1) {
        filePath = cursor.getString(columnIndex)
      }
      cursor.close()
    }
    return filePath
  }
}
