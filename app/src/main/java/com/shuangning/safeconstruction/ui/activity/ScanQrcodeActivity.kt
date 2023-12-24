package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.bean.other.QRcodeResult
import com.shuangning.safeconstruction.databinding.ActivityScanQrcodeBinding
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils2.JsonUtils
import com.shuangning.safeconstruction.utils2.MyLog


/**
 * Created by Chenwei on 2023/10/15.
 */
class ScanQrcodeActivity : BaseActivity<ActivityScanQrcodeBinding>(), QRCodeView.Delegate {
    private var isOpenFlashLight = false
    private var userId: Int = -1
    private var deviceId: Int = -1
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityScanQrcodeBinding? {
        return ActivityScanQrcodeBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.zxingview?.setDelegate(this)
        binding?.zxingview?.changeToScanQRCodeStyle() // 切换成扫描二维码样式
        binding?.zxingview?.setType(BarcodeType.ALL, null) // 识别所有类型的码
    }

    override fun initData() {

    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.back?.setOnClickListener {
            finish()
        }
        binding?.flashLight?.setOnClickListener {
            if (isOpenFlashLight) {
                binding?.zxingview?.closeFlashlight()
            } else {
                binding?.zxingview?.openFlashlight()
            }
            isOpenFlashLight = !isOpenFlashLight
        }

        binding?.photo?.setOnClickListener {
            selectPicture()
        }
    }

    private fun selectPicture() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: ArrayList<LocalMedia?>) {
                    result.takeIf {
                        !it.isNullOrEmpty()
                    }?.let {
                        it[0]?.realPath?.apply {
                            binding?.zxingview?.decodeQRCode(this)
                        }
                    }
                }

                override fun onCancel() {}
            })
    }

    override fun observeViewModel() {
    }

    override fun onResume() {
        super.onResume()
        binding?.zxingview?.startCamera()
        binding?.zxingview?.startSpotAndShowRect()
    }

    override fun onPause() {
        super.onPause()
        binding?.zxingview?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.zxingview?.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String?) {
        result?.let {
            MyLog.d("result: $it")
            try{
                val data = JsonUtils.fromJson<QRcodeResult>(result)
                data?.let {
                    if (it.userId > -1 ){
                        if (userId == it.userId){
                            return@let
                        }
                        userId = it.userId
                        deviceId = -1
                        QrcodeResultActivity.startTo(this@ScanQrcodeActivity, userId)
                    }
                    if (it.deviceId > -1){
                        if (deviceId == it.deviceId){
                            return@let
                        }
                        userId = -1
                        deviceId = it.deviceId
                        DeviceActivity.startTo(this@ScanQrcodeActivity, deviceId)
                    }

                }
            }catch (e: Exception){
                MyLog.e(e.message.toString())
            }
        }
        binding?.zxingview?.startSpot()

    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {
    }
}