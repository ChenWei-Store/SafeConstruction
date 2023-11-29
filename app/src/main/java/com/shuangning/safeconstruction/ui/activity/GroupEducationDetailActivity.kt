package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.databinding.ActivityGroupEducationDetailBinding
import com.shuangning.safeconstruction.ui.adapter.GroupEducationDetailAdapter
import com.shuangning.safeconstruction.ui.viewmodel.GroupEducationDetailViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils
import retrofit2.http.HEAD

/**
 * Created by Chenwei on 2023/11/5.
 */
class GroupEducationDetailActivity: BaseActivity<ActivityGroupEducationDetailBinding>() {
    private var detailAdapter: GroupEducationDetailAdapter?= null
    private var data: MutableList<IItemViewType> = mutableListOf()
    private val trainTopic: String by lazy{
        intent?.getStringExtra(TRAIN_TOPIC)?:""
    }
    private val viewModel by viewModels<GroupEducationDetailViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityGroupEducationDetailBinding? {
        return ActivityGroupEducationDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("班组教育")
        detailAdapter = GroupEducationDetailAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@GroupEducationDetailActivity)
            adapter = detailAdapter
        }
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getData(trainTopic)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            it?.let {
                it.type = HEADER
                data.add(it)
                detailAdapter?.notifyDataSetChanged()
                LoadingManager.stopLoading()
            }
        }
    }

    companion object{
        const val TRAIN_TOPIC = "train_topic"
        fun startTo(ctx: Context, trainTopic: String){
            ActivityUtils.start(ctx, GroupEducationDetailActivity::class.java){
                putExtra(TRAIN_TOPIC, trainTopic)
            }
        }
    }
}