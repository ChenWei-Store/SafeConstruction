package com.shuangning.safeconstruction.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.databinding.ActivityCameraDetailBinding
import com.shuangning.safeconstruction.manager.CAMERA_INFO
import com.shuangning.safeconstruction.ui.viewmodel.CameraViewModel
import com.shuangning.safeconstruction.utils.ToastUtil
import com.videogo.openapi.EZConstants
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.EZOpenSDKListener
import com.videogo.openapi.EZOpenSDKListener.EZStreamDownloadCallback
import com.videogo.openapi.EZPlayer
import java.io.File

class CameraDetailActivity : BaseActivity<ActivityCameraDetailBinding>() {
    private val viewModel by viewModels<CameraViewModel>()
    private var cameraInfo: CameraListResp? = null
    private var player: EZPlayer? = null
    private val videoPath by lazy { "${cacheDir}/temp.mp4" }
    private val recording: MutableLiveData<Boolean> = MutableLiveData(false)
    private val lock = Object()
    private val mSecondHandlerThread by lazy { HandlerThread("player") }
    private val mSecondHandler: Handler by lazy { getSecondHandler() }
    private var playing = false

    private fun getSecondHandler(): Handler {
        mSecondHandlerThread.start()
        return Handler(mSecondHandlerThread.looper, object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS -> playing = true
                }
                return true
            }

        })
    }

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

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> player?.startRealPlay()
                    Lifecycle.Event.ON_PAUSE -> {
                        player?.stopRealPlay()
                        if (true == recording.value) {
                            toggleRecord()
                        }
                        playing = false
                    }

                    Lifecycle.Event.ON_DESTROY -> player?.release()
                    else -> Unit
                }
            }
        })
        binding?.layoutSaveVideo?.setOnClickListener {
            toggleRecord()
        }
        cameraInfo?.let{
            binding?.imgBtn?.setOnTouchListener(viewModel.getControlListener(it))
        }
    }

    private fun toggleRecord() {
        synchronized(lock) {
            if (playing) {
                if (true == recording.value) {
                    player?.stopLocalRecord()
                } else {
                    val file = File(videoPath)
                    if (file.exists()) {
                        file.delete()
                    }
                    player?.startLocalRecordWithFile(videoPath)
                    recording.value = true
                }
            } else {
                ToastUtil.showCustomToast(R.string.monitor_start_record_fail)
            }
        }
    }

    private fun initPlayer(token: String) {
        EZOpenSDK.getInstance().setAccessToken(token)
        player = EZOpenSDK.getInstance()
            .createPlayer(cameraInfo?.deviceSerialNo, cameraInfo?.deviceNo?.toInt() ?: 0)
        binding?.surface?.holder?.let { player?.setSurfaceHold(it) }
        player?.setHandler(mSecondHandler)
        player?.setStreamDownloadCallback(object : EZStreamDownloadCallback {
            override fun onSuccess(path: String?) {
                path?.let {
                    viewModel?.saveVideo(
                        this@CameraDetailActivity, "${System.currentTimeMillis()}", File(it)
                    ){
                        recording.value = false
                    }
                }
            }

            override fun onError(p0: EZOpenSDKListener.EZStreamDownloadError?) {
            }
        })
        player?.startRealPlay()
    }

    override fun observeViewModel() {
        viewModel.cameraToken.observe(this) {
            initPlayer(it)
        }
        recording.observe(this) {
            binding?.tvRecord?.text =
                getString(if (true == recording.value) R.string.monitor_save_videoing else R.string.monitor_save_video)
            if (true == recording.value) {
                ToastUtil.showCustomToast(R.string.monitor_start_record_success)
            }
        }
    }

}