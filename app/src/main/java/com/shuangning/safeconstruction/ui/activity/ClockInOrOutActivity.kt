package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.SpatialRelationUtil
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.GetProjectBaseInfoResp
import com.shuangning.safeconstruction.databinding.ActivityClockInOutBinding
import com.shuangning.safeconstruction.extension.safeObjects
import com.shuangning.safeconstruction.ui.viewmodel.ClockInOutViewModel
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor
import com.shuangning.safeconstruction.utils2.BaiduLocation
import com.shuangning.safeconstruction.utils2.MyLog
import java.lang.ref.WeakReference
import java.util.Date
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Chenwei on 2023/10/18.
 */
class ClockInOrOutActivity : BaseActivity<ActivityClockInOutBinding>(),
    BaiduLocation.OnLocationResultCallback {
    private var timer: Timer? = null
    private var timeTask: TimeTask? = null
    private var result: BaiduLocation.LocationResult? = null
    private val viewModel by viewModels<ClockInOutViewModel>()
    private var isRequestNetEnd = false
    private var isRequestLocationEnd = false
    private var projectBaseInfo: GetProjectBaseInfoResp? = null
    private var isInScope = false
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityClockInOutBinding? {
        return ActivityClockInOutBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤打卡")
        showUI()
    }

    private fun showUI() {
        binding?.iv?.setBackgroundResource(R.drawable.circle_13ce7f)
        binding?.tvClockInOut?.text = "考勤打卡"
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        isRequestNetEnd = false
        isRequestLocationEnd = false
        viewModel.getData()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.iv?.setOnClickListener {
//            if (!isInScope) {
//                ToastUtil.showCustomToast("超出指定的打卡范围")
//                return@setOnClickListener
//            }
            result?.let {
                LoadingManager.startLoading(this)
                viewModel.perform(it.longitude.toString(), it.latitude.toString())
            }
        }
    }

    /**
     * 返回是否在打卡范围内
     *
     * @return 返回值
     *var0表示圆心的坐标，var1代表圆心的半径，var2代表要判断的点是否在圆内
     *isCircleContainsPoint(LatLng var0, int var1, LatLng var2);
     */
    private fun isRange(): Boolean {
        var projectLatLng: LatLng? = null
        projectBaseInfo?.let {
            projectLatLng = LatLng(it.latitude, it.longitude)
        }
        var curLatLng: LatLng? = null
        result?.let {
            curLatLng = LatLng(it.latitude, it.longitude)
        }
        var isRange = true
        projectLatLng.safeObjects(curLatLng) {
            isRange =
                SpatialRelationUtil.isCircleContainsPoint(it[0] as LatLng, 2000, it[1] as LatLng)
        } ?: let {
            isRange = false
        }
        return isRange
    }

    override fun onStart() {
        super.onStart()
        LoadingManager.startLoading(this)
        startTimer()
        BaiduLocation.start(this)
    }

    private fun startTimer() {
        binding?.tvTime?.let {
            if (timeTask == null) {
                timeTask = TimeTask(it)
            }
            if (timer == null) {
                timer = Timer()
            }
            timer?.schedule(timeTask, 0, 1000)
        }

    }

    override fun onStop() {
        super.onStop()
        timeTask?.cancel()
        timer?.cancel()
        timeTask = null
        timer = null
        BaiduLocation.stop()
        LoadingManager.stopLoading()
    }

    override fun observeViewModel() {
        viewModel.result.observe(this) {
            it?.let {
                if (it) {
                    ToastUtil.showCustomToast("打卡成功")
                } else {
                    ToastUtil.showCustomToast("打卡失败")
                }
                LoadingManager.stopLoading()
            }
        }
        viewModel.data.observe(this) {
            isRequestNetEnd = true
            projectBaseInfo = it
            mixData()
        }
    }

    private fun mixData() {
        if (isRequestNetEnd && isRequestLocationEnd) {
            LoadingManager.stopLoading()
            isInScope = isRange()
            if (isInScope) {
                binding?.viewBottom?.visibility = View.GONE
                binding?.tvBottom?.visibility = View.GONE
            } else {
                binding?.viewBottom?.visibility = View.VISIBLE
                binding?.tvBottom?.visibility = View.VISIBLE
            }
        }
    }

    class TimeTask : TimerTask {
        private var tvRef: WeakReference<TextView>? = null

        constructor(tv: TextView) {
            tvRef = WeakReference(tv)
        }

        override fun run() {
            if (tvRef == null || tvRef?.get() == null) {
                return
            }
            val time = TimeUtils.parseTime(Date(), TimeUtils.HH_mm_ss)
            ArchTaskExecutor.getInstance().postToMainThread {
                tvRef?.get()?.text = time
            }
        }
    }

    override fun onLocationResult(isSuccess: Boolean, result: BaiduLocation.LocationResult?) {
        MyLog.d("onLocationResult")
        if (isSuccess && result != null) {
            this.result = result
            binding?.tvLocation?.text = result.province + result.city +
                    result.district + result.town + result.street + result.locationDescribe
            MyLog.d("latitude:${result.latitude} longitude:${result.longitude}")
        }
        isRequestLocationEnd = true
        mixData()
    }
}