package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.databinding.ActivityCameraDetailBinding
import com.shuangning.safeconstruction.manager.CAMERA_INFO
import com.shuangning.safeconstruction.ui.viewmodel.CameraViewModel
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.EZPlayer

class CameraDetailActivity : BaseActivity<ActivityCameraDetailBinding>() {
    private val viewModel by viewModels<CameraViewModel>()
    private var cameraInfo: CameraListResp? = null
    private var player: EZPlayer? = null

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCameraDetailBinding? {
        return ActivityCameraDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        cameraInfo = intent?.getSerializableExtra(CAMERA_INFO) as CameraListResp?
        binding?.viewTitle?.setTitle(cameraInfo?.deviceName ?: "")
        viewModel.getCameraToken()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
        viewModel.cameraToken.observe(this) {
            it?.let {
                EZOpenSDK.getInstance().setAccessToken(it)
                player = EZOpenSDK.getInstance().createPlayer("AX2947957", 1)
                binding?.surface?.holder?.let{player?.setSurfaceHold(it)}
                player?.startRealPlay()
            }
        }
    }
}