package com.shuangning.safeconstruction.ui.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import com.videogo.openapi.EZConstants
import com.videogo.openapi.EZOpenSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Date


class CameraViewModel : ViewModel() {
    val cameraList: MutableLiveData<MutableList<CameraListResp>> = MutableLiveData()
    val cameraToken: MutableLiveData<String> = MutableLiveData()

    fun getCameraList() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getCameraList()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.let {
                cameraList.postValue(it)
            }
        }
    }

    fun getCameraToken() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getCameraToken()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.data?.accessToken?.let {
                cameraToken.postValue(it)
            }
        }
    }

    fun saveVideo(mContext: Context, name: String, srcFile: File, cb: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val uri = saveVideoToAlbum(mContext, name, srcFile)
            withContext(Dispatchers.Main) {
                cb()
                uri?.let {
                    ToastUtil.showCustomToast(R.string.monitor_save_record_success)
                }
            }
        }
    }

    fun saveVideoToAlbum(mContext: Context, name: String, srcFile: File): Uri? {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        var title = name
        try {
            val index = name.lastIndexOf(".")
            if (index > -1) {
                title = name.substring(0, index)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        contentValues.put(MediaStore.Video.Media.TITLE, title)
        val time: Long = Date().time
        contentValues.put(MediaStore.Video.Media.DATE_TAKEN, time)
        contentValues.put(MediaStore.Video.Media.DATE_ADDED, time / 1000)
        contentValues.put(MediaStore.Video.Media.DATE_MODIFIED, time / 1000)
        contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DCIM
            )
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, false)
        }
        val insertUri =
            mContext.contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
                ?: return null
        var ostream: FileOutputStream? = null
        var inputStream: InputStream? = null
        var fd: ParcelFileDescriptor? = null
        try {
            inputStream = FileInputStream(srcFile)
            fd = mContext.contentResolver.openFileDescriptor(insertUri, "w")
            ostream = FileOutputStream(fd!!.fileDescriptor)
            val buffer = ByteArray(1024 * 4)
            var n: Int
            while (inputStream.read(buffer).also { n = it } != -1) {
                ostream.write(buffer, 0, n)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            if (ostream != null) {
                try {
                    ostream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (fd != null) {
                try {
                    fd.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return insertUri
    }

    @SuppressLint("ClickableViewAccessibility")
    fun getControlListener(cameraInfo: CameraListResp): View.OnTouchListener {
        return View.OnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    val centerX = v.width / 2
                    val centerY = v.height / 2
                    val difCenterLen = Math.sqrt(
                        Math.pow((event.x - centerX).toDouble(), 2.0) +
                                Math.pow((event.y - centerY).toDouble(), 2.0)
                    )
                    if (difCenterLen / centerX > 0.39 && difCenterLen / centerX < 0.81) {
                        val sin = (event.y - centerY) / difCenterLen
                        val cos = (event.x - centerX) / difCenterLen
                        val temp = 1 / Math.sqrt(2.0)
                        if (sin > -temp && sin < temp && cos > temp) {
                            controlCamera(EZConstants.EZPTZCommand.EZPTZCommandRight, cameraInfo)
                        } else if (sin > -temp && sin < temp && cos < -temp) {
                            controlCamera(EZConstants.EZPTZCommand.EZPTZCommandLeft, cameraInfo)
                        } else if (sin < -temp && cos > -temp && cos < temp) {
                            controlCamera(EZConstants.EZPTZCommand.EZPTZCommandUp, cameraInfo)
                        } else if (sin > temp && cos > -temp && cos < temp) {
                            controlCamera(EZConstants.EZPTZCommand.EZPTZCommandDown, cameraInfo)
                        }
                    }
                }
            }
            true
        }
    }

    fun controlCamera(ori: EZConstants.EZPTZCommand, cameraInfo: CameraListResp) {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.runCatching {
                EZOpenSDK.getInstance().controlPTZ(
                    cameraInfo?.deviceSerialNo, cameraInfo?.deviceNo?.toInt() ?: 0,
                    ori, EZConstants.EZPTZAction.EZPTZActionSTART, 2
                )
                delay(2000)
                EZOpenSDK.getInstance().controlPTZ(
                    cameraInfo?.deviceSerialNo, cameraInfo?.deviceNo?.toInt() ?: 0,
                    ori, EZConstants.EZPTZAction.EZPTZActionSTOP, 2
                )
            }
        }

    }
}