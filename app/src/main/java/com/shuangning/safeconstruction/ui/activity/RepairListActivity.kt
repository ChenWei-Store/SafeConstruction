package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.DeviceRepairItem
import com.shuangning.safeconstruction.databinding.ActivityRepairListBinding
import com.shuangning.safeconstruction.ui.adapter.RepairAdapter
import com.shuangning.safeconstruction.ui.viewmodel.RepairListViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/12/23.
 */
class RepairListActivity: BaseActivity<ActivityRepairListBinding>() {
    private var repairAdapter: RepairAdapter?= null
    private val data = mutableListOf<DeviceRepairItem>()
    private val viewModel by viewModels<RepairListViewModel>()
    private val id: Int by lazy {
        intent?.getIntExtra(ID, -1)?:-1
    }
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRepairListBinding? {
       return ActivityRepairListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("维修保养记录")
        repairAdapter = RepairAdapter(data)
        binding?.rv?.apply {
            adapter = repairAdapter
            layoutManager = LinearLayoutManager(this@RepairListActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        LoadingManager.startLoading(this)
        viewModel.getData(id)
    }
    override fun initData() {

    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewBottom?.setOnClickListener {
            CommitRepairActivity.startTo(this, id)
        }
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            data.clear()
            it?.let {
                data.addAll(it)
                repairAdapter?.notifyDataSetChanged()
            }
            LoadingManager.stopLoading()
        }
    }
    companion object{
        const val ID = "id"
        fun startTo(ctx: Context, id: Int){
            ActivityUtils.start(ctx, RepairListActivity::class.java){
                putExtra(ID, id)
            }
        }
    }
}