package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
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
        val isInScope = true
        if (isInScope){
            binding?.iv?.setBackgroundResource(R.drawable.circle_13ce7f)
            binding?.tvClockInOut?.text = "外勤打卡"
        }else{
            binding?.iv?.setBackgroundResource(R.drawable.circle_main_color)
            binding?.tvClockInOut?.text = "考勤打卡"
        }
        result?.let {
            binding?.tvLocation?.text =  "${it.province + it.city +
                    it.district + it.town + it.street + it.locationDescribe}"
        }
        binding?.tvTime?.text = "18:43:41"
        binding?.tvTitle?.text="高淳至宣城高速公路江苏段工程项目"
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
                LoadingManager.startLoading(this)
                viewModel.perform(it.longitude.toString(), it.latitude.toString())
            }
//            //TODO:打卡接口
        }
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