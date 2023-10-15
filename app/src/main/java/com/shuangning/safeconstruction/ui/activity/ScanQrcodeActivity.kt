package com.shuangning.safeconstruction.ui.activity

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.permissionx.guolindev.PermissionX
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityScanQrcodeBinding

/**
 * Created by Chenwei on 2023/10/15.
 */
class ScanQrcodeActivity: BaseActivity<ActivityScanQrcodeBinding>(), QRCodeView.Delegate {
    private val permissions = arrayListOf(
        Manifest.permission.CAMERA)
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityScanQrcodeBinding? {
        return ActivityScanQrcodeBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.zxingview?.setDelegate(this)
//        binding?.zxingview?.hiddenScanRect()
    }

    override fun initData() {

    }
    private fun reqPermission(){
        PermissionX.init(this)
            .permissions(permissions)
            .request { allGranted, grantedList, deniedList ->
                if (!allGranted){
                    finish()
                }else{
                    binding?.zxingview?.startCamera()
                    binding?.zxingview?.startSpotAndShowRect()
                }
            }
    }
    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
    }

    override fun onStart() {
        super.onStart()
        reqPermission()
    }

    override fun onStop() {
        super.onStop()
        binding?.zxingview?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.zxingview?.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String?) {
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {
    }
}