package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.DeviceSensingItem
import com.shuangning.safeconstruction.databinding.ActivitySensingListBinding
import com.shuangning.safeconstruction.ui.adapter.SensingAdapter
import com.shuangning.safeconstruction.ui.viewmodel.SensingListViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/12/23.
 */
class SensingListActivity: BaseActivity<ActivitySensingListBinding>() {
    private var sensingAdapter: SensingAdapter?= null
    private val data = mutableListOf<DeviceSensingItem>()
    private val viewModel by viewModels<SensingListViewModel>()
    private val id: Int by lazy {
        intent?.getIntExtra(ID, -1)?:-1
    }
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySensingListBinding? {
       return ActivitySensingListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("检测检验记录")
        sensingAdapter = SensingAdapter(data)
        binding?.rv?.apply {
            adapter = sensingAdapter
            layoutManager = LinearLayoutManager(this@SensingListActivity)
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
            CommitSensingActivity.startTo(this, id)
        }
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            data.clear()
            it?.let {
                data.addAll(it)
                sensingAdapter?.notifyDataSetChanged()
            }
            LoadingManager.stopLoading()
        }
    }
    companion object{
        const val ID = "id"
        fun startTo(ctx: Context, id: Int){
            ActivityUtils.start(ctx, SensingListActivity::class.java){
                putExtra(ID, id)
            }
        }
    }
}