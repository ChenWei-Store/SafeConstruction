package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersBinding
import com.shuangning.safeconstruction.manager.FROM_TAKE_PHOTO_OF_DANAGE
import com.shuangning.safeconstruction.manager.FROM_WHERE
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersAdapter
import com.shuangning.safeconstruction.ui.viewmodel.SelectionViewModel
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersActivity: BaseActivity<ActivityTakePhotosOfDangersBinding>() {
    private var takePhotoOfDangerAdapter:TakePhotosOfDangersAdapter? = null
    private val data: MutableList<String> = mutableListOf()
    private var fromWhere = NONE
    private val viewModel by viewModels<SelectionViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersBinding? {
        return ActivityTakePhotosOfDangersBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (fromWhere == FROM_TAKE_PHOTO_OF_DANAGE){
            binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        }else{
            binding?.viewTitle?.setTitle(UIUtils.getString(R.string.group_education))
        }
        takePhotoOfDangerAdapter = TakePhotosOfDangersAdapter(data)
        binding?.rv?.apply {
            adapter = takePhotoOfDangerAdapter
            layoutManager = LinearLayoutManager(this@TakePhotosOfDangersActivity)
            addItemDecoration(DividerItemDecoration(this@TakePhotosOfDangersActivity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun initData() {
        fromWhere = intent?.getIntExtra(FROM_WHERE, FROM_TAKE_PHOTO_OF_DANAGE)?:FROM_TAKE_PHOTO_OF_DANAGE
        LoadingManager.startLoading(this)
        viewModel.getData()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        takePhotoOfDangerAdapter?.setOnItemClickListener(object: OnItemClickListener<String>{
            override fun onItemClick(data: String, position: Int) {
                if (fromWhere == FROM_TAKE_PHOTO_OF_DANAGE){
                    StartActivityManager.startToTakePhotosOfDangersStatus(this@TakePhotosOfDangersActivity)
                }else{
                    GroupEducationListActivity.startTo(this@TakePhotosOfDangersActivity, data)
                }
            }
        })

    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            if (it != null){
                data.addAll(it)
                takePhotoOfDangerAdapter?.notifyDataSetChanged()
            }
            LoadingManager.stopLoading()
        }
    }
}