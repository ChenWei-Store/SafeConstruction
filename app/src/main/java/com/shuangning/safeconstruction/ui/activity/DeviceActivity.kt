package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.DeviceResp
import com.shuangning.safeconstruction.databinding.ActivityDeviceBinding
import com.shuangning.safeconstruction.ui.viewmodel.DeviceViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/12/23.
 */
class DeviceActivity: BaseActivity<ActivityDeviceBinding>() {
    private val id: Int by lazy {
        intent?.getIntExtra(DeviceActivity.ID, -1)?:-1
    }
    private val viewModel by viewModels<DeviceViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityDeviceBinding? {
       return ActivityDeviceBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("设备详情")
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getData(id)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.view8?.setOnClickListener {
            //检验检测记录
            SensingListActivity.startTo(this@DeviceActivity, id)
        }
        binding?.view9?.setOnClickListener {
            //维修保养记录
            RepairListActivity.startTo(this, id)
        }
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            it?.let {
                init(it)
            }
            LoadingManager.stopLoading()
        }
    }

    private fun init(deviceResp: DeviceResp) {
        binding?.let {
            ImageLoader.loadUrl(this, deviceResp.shebeizhaopian, it.ivHeader)
            it.tvName.text = "${deviceResp.shebeimingcheng}  ${deviceResp.shebeibianma}"
            it.tvNumber.text = deviceResp.guigexinghao
            it.tvType.text = deviceResp.shebeifenlei
            it.tvAddress.text = deviceResp.shiyongbuwei
            it.tvPerson.text = deviceResp.caozuofuzeren
            it.tvRent.text = deviceResp.shifouzudai
            it.tvTime.text = deviceResp.jinchangshijian
            it.tvTest.text = deviceResp.jianyan
        }
    }
    companion object{
        const val ID = "id"
        fun startTo(ctx: Context, id: Int){
            ActivityUtils.start(ctx, DeviceActivity::class.java){
                putExtra(ID, id)
            }
        }

    }
}