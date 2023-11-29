package com.shuangning.safeconstruction.ui.activity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
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
import androidx.core.content.PermissionChecker
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityCaptureVideoBinding
import com.shuangning.safeconstruction.utils2.MyLog
import java.io.File
import java.text.SimpleDateFormat

/**
 * Created by Chenwei on 2023/11/27.
 */
class CaptureVideoActivity : BaseActivity<ActivityCaptureVideoBinding>() {
    private var videoCapture: VideoCapture<Recorder>? = null
    private var isStart = false
    private var recording: Recording? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var preview: Preview?= null
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
                bindPreview()
            }
        }, mainThreadExecutor)
    }

    private fun bindPreview() {
        preview = Preview.Builder().build()

        binding?.preview?.surfaceProvider?.let {
            preview?.setSurfaceProvider(it)
        }
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.LOWEST))
            .build()
        videoCapture = VideoCapture.withOutput(recorder)
        try {
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                this, CameraSelector.DEFAULT_BACK_CAMERA, preview, videoCapture)
        } catch(exc: Exception) {
            MyLog.e( "Use case binding failed:${exc.message}")
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

    private fun start(){
        val name =System.currentTimeMillis()
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
                if (PermissionChecker.checkSelfPermission(this@CaptureVideoActivity,
                        Manifest.permission.RECORD_AUDIO) ==
                    PermissionChecker.PERMISSION_GRANTED)
                {
                    withAudioEnabled()
                }
            }
            ?.start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Start -> {
                        isStart = true
                        binding?.btnStart?.text = "完成"
                    }
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val uri = recordEvent.outputResults.outputUri
                            MyLog.d("uri1:$uri")
                            val intent = Intent()
                            intent.putExtra("path", uri.toString())
                            setResult(RESULT_OK, intent)
                            finish()
                            binding?.btnStart?.visibility = View.GONE
                        } else {
                            recording?.close()
                            recording = null
                            binding?.btnStart?.text = "开始"
                            MyLog.d("Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }
                        isStart = false
                    }
                }
            }
    }
//    private fun startRecord() {
//        val name = "${System.currentTimeMillis()}.mp4"
//        val contentValues = ContentValues().apply {
//            put(MediaStore.Video.Media.DISPLAY_NAME, name)
//            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
//        }
//        val filepath = this.getExternalFilesDir(null)?.absolutePath + File.separator +
//                "camerax_video"
//        var videoPath = filepath + File.separator + "video_${System.currentTimeMillis()}.mp4"
//        val videoFile = File(videoPath)
//
//        val fileOutput = FileOutputOptions.Builder(videoFile).build()
//        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
//            this.contentResolver,
//            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//        )
//            .setContentValues(contentValues)
//            .build()
//        MyLog.d("uri:" + mediaStoreOutput.collectionUri)
//        MyLog.d("location:" + mediaStoreOutput.location)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.RECORD_AUDIO
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        recording = videoCapture?.output
//            ?.prepareRecording(this, fileOutput)
//            ?.withAudioEnabled()
//            ?.start(ContextCompat.getMainExecutor(this)) {
//                when (it) {
//                    is VideoRecordEvent.Finalize -> {
//                        if (!it.hasError()) {
//                            val path = it.outputResults.outputUri.path
//                            val intent = Intent()
//                            intent.putExtra("path", path)
//                            setResult(RESULT_OK, intent)
//                            finish()
//                        } else {
//                            recording?.close()
//                            recording = null
//                            MyLog.e("Video capture ends with error: " + "${it.error}")
//                        }
//                    }
//                }
//            }
//    }

    protected fun unbindUseCases() {
        try {
            cameraProvider?.unbind(videoCapture)
        } catch (e: Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        recording?.close()
//        recording = null
//        unbindUseCases()
    }
    override fun observeViewModel() {
    }
}