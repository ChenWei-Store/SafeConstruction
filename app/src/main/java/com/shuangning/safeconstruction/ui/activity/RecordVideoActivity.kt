package com.shuangning.safeconstruction.ui.activity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityRecordVideoBinding
import com.shuangning.safeconstruction.extension.getAspectRatio
import com.shuangning.safeconstruction.extension.getAspectRatioString
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.MyLog
import kotlinx.coroutines.async

/**
 * Created by Chenwei on 2023/11/27.
 */
class RecordVideoActivity : BaseActivity<ActivityRecordVideoBinding>() {
    private var videoCapture: VideoCapture<Recorder>? = null
    private var isStart = false
    private var recording: Recording? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var preview: Preview? = null
    private val mainThreadExecutor by lazy {
        ContextCompat.getMainExecutor(this)
    }


    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRecordVideoBinding? {
        return ActivityRecordVideoBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            cameraProvider?.let {
                bindPreview()
            }
        }, mainThreadExecutor)
    }


    private fun bindPreview() {
        val quality = Quality.SD
        val qualitySelector = QualitySelector.from(quality)
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        binding?.preview?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            val orientation = this@RecordVideoActivity.resources.configuration.orientation
            dimensionRatio = quality.getAspectRatioString(
                quality,
                (orientation == Configuration.ORIENTATION_PORTRAIT)
            )
        }

        preview = Preview.Builder()
            .setTargetAspectRatio(quality.getAspectRatio(quality))
            .build()
        binding?.preview?.surfaceProvider?.let {
            preview?.setSurfaceProvider(it)
        }
        val recorder = Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()
        videoCapture = VideoCapture.withOutput(recorder)

        try {
            cameraProvider?.apply {
                if (!hasCamera(cameraSelector)) {
                    ToastUtil.showCustomToast("当前手机不支持后置摄像头")
                    finish()
                    return
                }
                unbindAll()
                bindToLifecycle(
                    this@RecordVideoActivity, cameraSelector, preview, videoCapture
                )
            }

        } catch (exc: Exception) {
            MyLog.e("Use case binding failed:${exc.message}")
        }
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.btnStart?.setOnClickListener {
            if (!isStart) {
                start()
                isStart = true
                binding?.btnStart?.text = "完成"
            } else {
                recording?.stop()
                recording = null
                isStart = false
                binding?.btnStart?.visibility = View.GONE
            }
        }
    }

    private fun start() {
        //指定视频相关参数(文件名，文件格式)
        val name = System.currentTimeMillis()
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }
        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        recording = videoCapture?.output
            ?.prepareRecording(this, mediaStoreOutputOptions)
            ?.apply {
                if (PermissionChecker.checkSelfPermission(
                        this@RecordVideoActivity,
                        Manifest.permission.RECORD_AUDIO
                    ) ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    withAudioEnabled()
                }
            }
            ?.start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when (recordEvent) {
                    is VideoRecordEvent.Start -> {
                        isStart = true
                        binding?.btnStart?.text = "完成"
                    }

                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val uri = recordEvent.outputResults.outputUri
                            MyLog.d("uri1:$uri")
                            val intent = Intent()
                            intent.putExtra("uri", uri.toString())
                            setResult(RESULT_OK, intent)
                            finish()
                            binding?.btnStart?.visibility = View.GONE
                        } else {
                            binding?.btnStart?.text = "开始"
                            MyLog.d(
                                "Video capture ends with error: " +
                                        "${recordEvent.error}"
                            )
                        }
                        isStart = false
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        recording?.close()
        recording = null
        cameraProvider?.unbindAll()
    }

    override fun observeViewModel() {
    }

    data class CameraCapability(val camSelector: CameraSelector, val qualities: List<Quality>)
}