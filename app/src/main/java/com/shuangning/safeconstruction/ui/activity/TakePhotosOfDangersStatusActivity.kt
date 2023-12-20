package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersStatusContent
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersStatusBinding
import com.shuangning.safeconstruction.extension.newTab
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersStatusMultiAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/10.
 */
const val NONE = -1
const val TO_BE_RECEIVED = 0
const val TO_BE_PROCESSED = 1
const val PRECESSED = 2
class TakePhotosOfDangersStatusActivity: BaseActivity<ActivityTakePhotosOfDangersStatusBinding>() {
    private var takePhotoOfDangerStatusAdapter:TakePhotosOfDangersStatusMultiAdapter? = null
    private val toBeReceivedData: MutableList<TakePhotosOfDangersStatusContent> = mutableListOf()
    private val toBeProcessedData: MutableList<TakePhotosOfDangersStatusContent> = mutableListOf()
    private val processedData: MutableList<TakePhotosOfDangersStatusContent> = mutableListOf()
    private var selectedIndex = 0
    private val dataMap = mutableMapOf<Int, MutableList<TakePhotosOfDangersStatusContent>>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersStatusBinding? {
        return ActivityTakePhotosOfDangersStatusBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        takePhotoOfDangerStatusAdapter = TakePhotosOfDangersStatusMultiAdapter(getCurData(selectedIndex))
        binding?.rv?.apply {
            adapter = takePhotoOfDangerStatusAdapter
            layoutManager = LinearLayoutManager(this@TakePhotosOfDangersStatusActivity)
        }
        takePhotoOfDangerStatusAdapter?.notifyDataSetChanged()
        binding?.tabLayout?.apply {
            addTab(newTab(R.string.to_be_received, toBeReceivedData.size), true)
            addTab(newTab( R.string.to_be_processed, toBeProcessedData.size))
//            addTab(newTab( R.string.processed, processedData.size))
        }
    }

    private fun selectTab(position: Int, tab: Tab?){
        if (position == NONE || position == selectedIndex){
            return
        }
        tab?: return
        selectedIndex = position
        tab.select()
        takePhotoOfDangerStatusAdapter?.refreshData(getCurData(selectedIndex))
    }

    private fun getCurData(position: Int): MutableList<TakePhotosOfDangersStatusContent>{
        return dataMap.getOrDefault(selectedIndex, mutableListOf())
    }

    override fun initData() {
        toBeReceivedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_RECEIVED, "封闭段还可以打开吗", "炎月"))
        toBeReceivedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_RECEIVED, "封闭段还可以打开吗", "炎月"))
        toBeReceivedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_RECEIVED, "封闭段还可以打开吗", "炎月"))
        toBeReceivedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_RECEIVED, "封闭段还可以打开吗", "炎月"))
        toBeReceivedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_RECEIVED, "封闭段还可以打开吗", "炎月"))

        toBeProcessedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_PROCESSED, "封闭段还可以打开吗", "炎月"))
        toBeProcessedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_PROCESSED, "封闭段还可以打开吗", "炎月"))
        toBeProcessedData.add(TakePhotosOfDangersStatusContent("2021-12-07", TO_BE_PROCESSED, "封闭段还可以打开吗", "炎月"))

        processedData.add(TakePhotosOfDangersStatusContent("2021-12-07", PRECESSED, "封闭段还可以打开吗", "炎月"))
        processedData.add(TakePhotosOfDangersStatusContent("2021-12-07", PRECESSED, "封闭段还可以打开吗", "炎月"))
        processedData.add(TakePhotosOfDangersStatusContent("2021-12-07", PRECESSED, "封闭段还可以打开吗", "炎月"))
        processedData.add(TakePhotosOfDangersStatusContent("2021-12-07", PRECESSED, "封闭段还可以打开吗", "炎月"))

        dataMap[TO_BE_RECEIVED] = toBeReceivedData
        dataMap[TO_BE_PROCESSED] = toBeProcessedData
        dataMap[PRECESSED] = processedData
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        takePhotoOfDangerStatusAdapter?.setOnItemClickListener(object: OnItemClickListener<TakePhotosOfDangersStatusContent>{
            override fun onItemClick(data: TakePhotosOfDangersStatusContent, position: Int) {
                StartActivityManager.startToTakePhotosOfDangersDetail(this@TakePhotosOfDangersStatusActivity)

            }
        })
        binding?.tabLayout?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val index = tab?.position?: NONE
                selectTab(index, tab)
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