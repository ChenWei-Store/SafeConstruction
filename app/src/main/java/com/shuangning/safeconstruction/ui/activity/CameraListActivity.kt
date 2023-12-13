package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.databinding.ActivityCameraListBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.CameraListAdapter
import com.shuangning.safeconstruction.ui.viewmodel.CameraViewModel
import com.shuangning.safeconstruction.utils.UIUtils

class CameraListActivity : BaseActivity<ActivityCameraListBinding>() {
    private val viewModel by viewModels<CameraViewModel>()
    val cameraList: MutableList<CameraListResp> = mutableListOf()
    private val cameraListAdapter: CameraListAdapter by lazy { CameraListAdapter(cameraList) }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCameraListBinding? {
        return ActivityCameraListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.video_surveillance))
        binding?.rv?.apply {
            adapter = cameraListAdapter
            addItemDecoration(
                DividerItemDecoration(this@CameraListActivity, DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun initData() {
        viewModel.getCameraList()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        cameraListAdapter.setOnItemClickListener(object : OnItemClickListener<CameraListResp>{
            override fun onItemClick(data: CameraListResp, position: Int) {
                StartActivityManager.startToCameraDetail(this@CameraListActivity, data)
            }
        })
    }

    override fun observeViewModel() {
        viewModel.cameraList.observe(this) {
            it?.let {
                cameraList.addAll(it)
                cameraListAdapter.notifyDataSetChanged()
            }
        }
    }
}