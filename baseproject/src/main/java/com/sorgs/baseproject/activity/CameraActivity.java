package com.sorgs.baseproject.activity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.sorgs.baseproject.R;
import com.sorgs.baseproject.base.BaseActivity;
import com.sorgs.baseproject.helper.ActivityStackManager;
import com.sorgs.baseproject.other.IntentKey;
import com.sorgs.baseproject.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/18
 * desc   : 拍照选择
 */
public final class CameraActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 1024;
    private File mFile;

    public static void start(BaseActivity activity, OnCameraListener listener) {
        XXPermissions.with(ActivityStackManager.getInstance().getTopActivity())
                     .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)
                     .request(new OnPermission() {

                         @Override
                         public void hasPermission(List<String> granted, boolean all) {
                             if (all) {
                                 File file = createCameraFile();
                                 Intent intent = new Intent(activity, CameraActivity.class);
                                 intent.putExtra(IntentKey.FILE, file);
                                 activity.startActivityForResult(intent, (resultCode, data) -> {

                                     if (listener == null) {
                                         return;
                                     }

                                     if (resultCode == RESULT_OK) {
                                         listener.onSelected(file);
                                     } else {
                                         listener.onCancel();
                                     }
                                 });
                             }
                         }

                         @Override
                         public void noPermission(List<String> denied, boolean quick) {
                             if (quick) {
                                 ToastUtils.INSTANCE.showShort(R.string.common_permission_fail);
                                 XXPermissions.gotoPermissionSettings(ActivityStackManager.getInstance().getTopActivity(), false);
                             } else {
                                 ToastUtils.INSTANCE.showShort(R.string.common_permission_hint);
                             }
                         }
                     });
    }

    /**
     * 创建一个拍照图片文件对象
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createCameraFile() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (!folder.exists() || !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                folder = Environment.getExternalStorageDirectory();
            }
        }

        try {
            File file = new File(folder, "IMG_" + new SimpleDateFormat("yyyyMMdd_kkmmss", Locale.getDefault()).format(new Date()) + ".jpg");
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int initLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (XXPermissions.isHasPermission(this, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)
                && intent.resolveActivity(getPackageManager()) != null) {
            mFile = getSerializable(IntentKey.FILE);
            if (mFile != null && mFile.exists()) {

                Uri imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 通过 FileProvider 创建一个 Content 类型的 Uri 文件
                    imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", mFile);
                } else {
                    imageUri = Uri.fromFile(mFile);
                }
                // 对目标应用临时授权该 Uri 所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // 将拍取的照片保存到指定 Uri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                ToastUtils.INSTANCE.showShort(R.string.photo_picture_error);
                finish();
            }
        } else {
            ToastUtils.INSTANCE.showShort(R.string.photo_launch_fail);
            finish();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    // 重新扫描多媒体（否则可能扫描不到）
                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{mFile.getPath()}, null, null);
                    break;
                case RESULT_CANCELED:
                    // 删除这个文件
                    mFile.delete();
                    break;
                default:
                    break;
            }
            setResult(resultCode);
            finish();
        }
    }

    /**
     * 拍照选择监听
     */
    public interface OnCameraListener {

        /**
         * 选择回调
         *
         * @param file 照片
         */
        void onSelected(File file);

        /**
         * 取消回调
         */
        default void onCancel() {
        }
    }
}