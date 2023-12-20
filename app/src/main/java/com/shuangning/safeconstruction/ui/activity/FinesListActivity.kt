package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.FinesListItem
import com.shuangning.safeconstruction.databinding.ActivityFinesBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.FinesMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.FinesListViewModel
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesListActivity: BaseActivity<ActivityFinesBinding>() {
    private var finesAdapter: FinesMultiAdapter?= null
    private val data: MutableList<FinesListItem> = mutableListOf()
    private val viewModel by viewModels<FinesListViewModel>()
    private var checkoutNo = ""
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityFinesBinding? {
        return ActivityFinesBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.fines_list))
        finesAdapter = FinesMultiAdapter(data)
        binding?.rv?.apply {
            adapter = finesAdapter
            layoutManager = LinearLayoutManager(this@FinesListActivity)
        }
    }

    override fun initData() {
        checkoutNo = intent?.getStringExtra(CHECK_OUT_NUM)?:""

    }

    override fun onResume() {
        super.onResume()
        LoadingManager.startLoading(this)
        viewModel.getData(checkoutNo)
    }
    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        finesAdapter?.setOnItemClickListener(object: OnItemClickListener<FinesListItem>{
            override fun onItemClick(data: FinesListItem, position: Int) {
                FinesDetailActivity.startTo(this@FinesListActivity, data.id)
            }
        })

        binding?.llBottom?.setOnClickListener {
            AddFinesActivity.startTo(this@FinesListActivity, checkoutNo)
        }
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            data.clear()
            it?.let {
                data.addAll(it)
            }
            finesAdapter?.notifyDataSetChanged()
            LoadingManager.stopLoading()
        }
    }

    companion object{
        const val CHECK_OUT_NUM = "checkOutNum"
        fun startTo(ctx: Context, checkoutNo: String){
            ActivityUtils.start(ctx, FinesListActivity::class.java){
                putExtra(CHECK_OUT_NUM, checkoutNo)
            }
        }
    }
}