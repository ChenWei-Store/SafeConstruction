package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.SpatialRelationUtil
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.databinding.ActivityClockInOutBinding
import com.shuangning.safeconstruction.ui.viewmodel.ClockInOutViewModel
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor
import com.shuangning.safeconstruction.utils2.BaiduLocation
import java.lang.ref.WeakReference
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Created by Chenwei on 2023/10/18.
 */
class ClockInOrOutActivity: BaseActivity<ActivityClockInOutBinding>(), BaiduLocation.OnLocationResultCallback {
    private var timer: Timer?= null
    private var timeTask: TimeTask?= null
    private var result: BaiduLocation.LocationResult?= null
    private val viewModel by viewModels<ClockInOutViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityClockInOutBinding? {
        return ActivityClockInOutBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤打卡")
        showUI()
    }

    private fun showUI(){
        binding?.iv?.setBackgroundResource(R.drawable.circle_13ce7f)
        binding?.tvClockInOut?.text = "考勤打卡"
        result?.let {
            binding?.tvLocation?.text =  "${it.province + it.city +
                    it.district + it.town + it.street + it.locationDescribe}"
        }
    }
    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.iv?.setOnClickListener {
            result?.let {
                if (isRange()){
                    LoadingManager.startLoading(this)
                    viewModel.perform(it.longitude.toString(), it.latitude.toString())
                }
            }
//            //TODO:打卡接口
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
        return true
//        return result?.let {
//            return SpatialRelationUtil.isCircleContainsPoint(
//                LatLng(it.latitude, it.longitude),
//                2000,
//                LatLng(39.93058, 116.440108)
//            )
//        }?:false
    }

    override fun onStart() {
        super.onStart()
        LoadingManager.startLoading(this)
        startTimer()
        BaiduLocation.start(this)
    }

    private fun startTimer(){
        binding?.tvTime?.let {
            if (timeTask == null){
                timeTask = TimeTask(it)
            }
            if (timer == null){
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
        viewModel.result.observe(this){
            it?.let {
                if (it){
                    ToastUtil.showCustomToast("打卡成功")
                }else{
                    ToastUtil.showCustomToast("打卡失败")
                }
                LoadingManager.stopLoading()
            }
        }
    }

    class TimeTask: TimerTask {
        private var tvRef: WeakReference<TextView>?= null
        constructor(tv: TextView){
            tvRef = WeakReference(tv)
        }
        override fun run() {
            if (tvRef == null || tvRef?.get() == null){
                return
            }
            val time = TimeUtils.parseTime(Date(), TimeUtils.HH_mm_ss)
            ArchTaskExecutor.getInstance().postToMainThread {
                tvRef?.get()?.text = time
            }
        }
    }

    override fun onLocationResult(isSuccess: Boolean, result: BaiduLocation.LocationResult?) {
        LoadingManager.stopLoading()
        if (isSuccess && result != null) {
            this.result = result
            showUI()
        }
    }
}