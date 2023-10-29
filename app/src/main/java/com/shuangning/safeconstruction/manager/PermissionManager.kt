package com.shuangning.safeconstruction.manager

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.shuangning.safeconstruction.utils.ToastUtil
import java.security.Permission

/**
 * Created by Chenwei on 2023/10/25.
 */
object PermissionManager {
    fun requestCamera(ctx: FragmentActivity, block:()-> Unit){
        if (PermissionX.isGranted(ctx, Manifest.permission.CAMERA)){
            block()
        }else{
            XPopCreateUtils.showConfirmCancelDialog(ctx, "", "应用程序需要拍照的权限，请授权"){
                PermissionX.init(ctx)
                    .permissions(Manifest.permission.CAMERA)
                    .request { allGranted, grantedList, deniedList ->
                        if (!allGranted){
                            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.CAMERA)) {
                                //是否会弹窗询问
                                ToastUtil.showCustomToast("必要的权限被拒绝")
                            }else{
                                XPopCreateUtils.showConfirmCancelDialog(ctx, "", "一些必要的权限被禁止，正常功能可能无法使用，请授权。"){
                                    goSetting(ctx)
                                }
                            }
                        }else{
                            block()
                        }
                    }
            }
        }
    }

    fun reqFile(ctx: FragmentActivity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            PermissionX.init(ctx).permissions(Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO)
                .request{ allGranted, grantedList, deniedList ->

                }
        }else{
            //Android 6-12
            PermissionX.init(ctx)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                .request{ allGranted, grantedList, deniedList ->

                }
        }
    }

    fun goSetting(ctx: FragmentActivity){
        //TODO:确认
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", ctx.packageName, null)
        intent.data = uri
        ctx.startActivity(intent)
    }

    fun reqLocation(ctx: FragmentActivity, block:()-> Unit) {
        if (PermissionX.isGranted(ctx, Manifest.permission.ACCESS_FINE_LOCATION) || PermissionX.isGranted(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            block()
        } else {
            XPopCreateUtils.showConfirmCancelDialog(
                ctx,
                "",
                "应用程序需要定位权限进行打卡，请授权"
            ) {
                PermissionX.init(ctx)
                    .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .request { allGranted, grantedList, deniedList ->
                        if (!allGranted) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    ctx,
                                    Manifest.permission.CAMERA
                                )
                            ) {
                                //是否会弹窗询问
                                ToastUtil.showCustomToast("必要的权限被拒绝")
                            } else {
                                XPopCreateUtils.showConfirmCancelDialog(
                                    ctx,
                                    "",
                                    "定位权限被禁止，正常功能可能无法使用，请授权。"
                                ) {
                                    goSetting(ctx)
                                }
                            }
                        } else {
                            block()
                        }
                    }
            }
        }
    }

}