package com.shuangning.safeconstruction.ui.activity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraMetadata
import android.os.Bundle
import android.provider.MediaStore
import android.util.Rational
import android.view.LayoutInflater
import android.view.View
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityCaptureVideoBinding
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor
import com.shuangning.safeconstruction.utils2.MyLog
import java.io.File

/**
 * Created by Chenwei on 2023/11/27.
 */
class CaptureVideoActivity : BaseActivity<ActivityCaptureVideoBinding>() {
    private var videoCapture: VideoCapture<Recorder>? = null
    private var isStart = false
    private var recording: Recording? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private val mainThreadExecutor by lazy {
        ContextCompat.getMainExecutor(this)
    }
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCaptureVideoBinding? {
        return ActivityCaptureVideoBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            cameraProvider?.let {
                bindPreview(it)
            }
        }, mainThreadExecutor)
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val qualitySelector = QualitySelector.fromOrderedList(
            listOf(Quality.SD),
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        )
        val recorder = Recorder.Builder()
            .setExecutor(ArchTaskExecutor.sIOThreadExecutor).setQualitySelector(qualitySelector)
            .build()

        videoCapture = VideoCapture.withOutput(recorder)
        val preview = Preview.Builder().build()
        binding?.let {
            preview.setSurfaceProvider(it.preview.surfaceProvider)
            val viewPort: ViewPort = ViewPort.Builder(
                Rational(
                    it.preview.width,
                    it.preview.height,
                ),
                it.preview.display.rotation
            ).build()
            val useCaseGroupBuilder: UseCaseGroup.Builder = UseCaseGroup.Builder()
                .setViewPort(viewPort)
                .addUseCase(preview)
            videoCapture?.let {
                useCaseGroupBuilder.addUseCase(it)
            }
            try {
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, useCaseGroupBuilder.build())
            } catch (exc: Exception) {
                MyLog.e("Use case binding failed:${exc.message}")
            }
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
                startRecord()
                isStart = true
                binding?.btnStart?.text = "完成"
            } else {
                recording?.stop()
                recording?.close()
                isStart = false
                binding?.btnStart?.visibility = View.GONE
            }
        }
    }

    private fun startRecord() {
        val name = "${System.currentTimeMillis()}.mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        }
        val filepath = this.getExternalFilesDir(null)?.absolutePath + File.separator +
                "camerax_video"
        var videoPath = filepath + File.separator + "video_${System.currentTimeMillis()}.mp4"
        val videoFile = File(videoPath)

        val fileOutput = FileOutputOptions.Builder(videoFile).build()
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            this.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
            .setContentValues(contentValues)
            .build()
        MyLog.d("uri:" + mediaStoreOutput.collectionUri)
        MyLog.d("location:" + mediaStoreOutput.location)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        recording = videoCapture?.output
            ?.prepareRecording(this, fileOutput)
            ?.withAudioEnabled()
            ?.start(ContextCompat.getMainExecutor(this)) {
                when (it) {
                    is VideoRecordEvent.Finalize -> {
                        if (!it.hasError()) {
                            val path = it.outputResults.outputUri.path
                            val intent = Intent()
                            intent.putExtra("path", path)
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            recording?.close()
                            recording = null
                            MyLog.e("Video capture ends with error: " + "${it.error}")
                        }
                    }
                }
            }
    }

    protected fun unbindUseCases() {
        try {
            cameraProvider?.unbind(videoCapture)
        } catch (e: Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recording?.close()
        recording = null
        unbindUseCases()
    }
    override fun observeViewModel() {
    }
}