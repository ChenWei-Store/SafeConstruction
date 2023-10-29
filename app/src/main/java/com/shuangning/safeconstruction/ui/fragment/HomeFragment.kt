package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.HomeContentBean
import com.shuangning.safeconstruction.bean.other.HomeHeaderBean
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.FragmentHomeBinding
import com.shuangning.safeconstruction.manager.HomeItemManager
import com.shuangning.safeconstruction.manager.PermissionManager
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.HomeMultiAdapter
import com.shuangning.safeconstruction.utils2.MyLog

/**
 * Created by Chenwei on 2023/10/7.
 */
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    private var data: MutableList<ItemViewType> = mutableListOf()
    private var adapter: HomeMultiAdapter?= null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding? {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = HomeMultiAdapter(data)
        val layoutManager = GridLayoutManager(activity, 4)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0){
                    return 4
                }
                return 1
            }
        }
        binding?.rvHome?.layoutManager = layoutManager
        binding?.rvHome?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun initData() {
        val items = HomeItemManager.getData()
        data.add(HomeHeaderBean())
        data.addAll(items)

    }

    override fun initListener() {
        adapter?.setOnItemClickListener(object: OnItemClickListener<ItemViewType>{
            override fun onItemClick(data: ItemViewType, position: Int) {
                if(position == 0){
                    return
                }
                val realData = data as? HomeContentBean
                activity?.apply {
                    when(realData?.functionId){
                        HomeItemManager.TAKE_PTOTOS_OF_DANGERS ->
                            StartActivityManager.startToTakePhotosOfDangers(this)
                        HomeItemManager.RECTIFICATION_AND_REPLY ->
                            StartActivityManager.startToRectificationAndReply(this)
                        HomeItemManager.ROUTINE_INSPCETION ->
                            StartActivityManager.startToRoutineInspectionList(this)
                        HomeItemManager.ATTENDANCE_MANAGEMENT ->
                            StartActivityManager.startAttendanceManagement(this)
                    }
                }
            }
        })

    }

    override fun observeViewModel() {
    }

    override fun isRegisterEventbus(): Boolean {
        return true
    }

    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)
        MyLog.d("receiveEvent:$code")
        when(code){
            EventCode.START_SCAN_QRCODE->{
                reqCameraPermissionAndStart()
            }
            EventCode.START_CLOCK_IN_OUT->{
              reqLocationPermissionAndStart()
            }
        }
    }

    private fun reqLocationPermissionAndStart(){
        (activity as? FragmentActivity)?.apply {
            PermissionManager.reqLocation(this){
                StartActivityManager.startToClockInOut(this)
            }

        }
    }

    private fun reqCameraPermissionAndStart(){
        (activity as? FragmentActivity)?.apply {
            PermissionManager.requestCamera(this){
                StartActivityManager.startToScanQrcode(this)
            }

        }
    }
}