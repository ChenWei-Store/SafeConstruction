package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersStatusContent
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersTitle
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersBinding
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersStatusBinding
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersStatusAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersStatusActivity: BaseActivity<ActivityTakePhotosOfDangersStatusBinding>() {
    private var takePhotoOfDangerStatusAdapter:TakePhotosOfDangersStatusAdapter? = null
    private val data: MutableList<TakePhotosOfDangersStatusContent> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersStatusBinding? {
        return ActivityTakePhotosOfDangersStatusBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        takePhotoOfDangerStatusAdapter = TakePhotosOfDangersStatusAdapter(data)
        binding?.rv?.apply {
            adapter = takePhotoOfDangerStatusAdapter
            layoutManager = LinearLayoutManager(this@TakePhotosOfDangersStatusActivity)
        }
        takePhotoOfDangerStatusAdapter?.notifyDataSetChanged()
    }

    override fun initData() {
        data.add(TakePhotosOfDangersStatusContent("2021-12-07", 1, "封闭段还可以打开吗", "炎月"))
        data.add(TakePhotosOfDangersStatusContent("2021-12-07", 1, "封闭段还可以打开吗", "炎月"))
        data.add(TakePhotosOfDangersStatusContent("2021-12-07", 1, "封闭段还可以打开吗", "炎月"))
        data.add(TakePhotosOfDangersStatusContent("2021-12-07", 1, "封闭段还可以打开吗", "炎月"))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        takePhotoOfDangerStatusAdapter?.setOnItemClickListener(object: OnItemClickListener<TakePhotosOfDangersStatusContent>{
            override fun onItemClick(data: TakePhotosOfDangersStatusContent, position: Int) {
            }
        })
        binding?.tabLayout?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun observeViewModel() {
    }
}