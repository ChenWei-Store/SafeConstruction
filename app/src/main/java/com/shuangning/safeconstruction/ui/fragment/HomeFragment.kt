package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jaeger.library.StatusBarUtil
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.HomeContentBean
import com.shuangning.safeconstruction.bean.other.HomeHeaderBean
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.FragmentHomeBinding
import com.shuangning.safeconstruction.manager.FROM_GROUP_EDUCATION
import com.shuangning.safeconstruction.manager.HomeItemManager
import com.shuangning.safeconstruction.manager.PermissionManager
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.FROM_TAKE_PHOTO_OF_DANAGE
import com.shuangning.safeconstruction.ui.adapter.HomeMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.HomeViewModel
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.MyLog

/**
 * Created by Chenwei on 2023/10/7.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var data: MutableList<ItemViewType> = mutableListOf()
    private var adapter: HomeMultiAdapter? = null
    private val viewModel by viewModels<HomeViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding? {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setColor(activity, UIUtils.getColor(R.color.white))
        adapter = HomeMultiAdapter(data)
        val layoutManager = GridLayoutManager(activity, 4)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
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
        activity?.apply {
            LoadingManager.startLoading(this)
        }
        viewModel.getData()

    }

    override fun initListener() {
        adapter?.setOnItemClickListener(object : OnItemClickListener<ItemViewType> {
            override fun onItemClick(data: ItemViewType, position: Int) {
                if (position == 0) {
                    return
                }
                val realData = data as? HomeContentBean
                activity?.apply {
                    when (realData?.functionId) {
                        HomeItemManager.TAKE_PTOTOS_OF_DANGERS ->
                            StartActivityManager.startToTakePhotosOfDangers(
                                this,
                                FROM_TAKE_PHOTO_OF_DANAGE
                            )

                        HomeItemManager.RECTIFICATION_AND_REPLY ->
                            StartActivityManager.startToRectificationAndReply(this)

                        HomeItemManager.ROUTINE_INSPCETION ->
                            StartActivityManager.startToRoutineInspectionList(this)

                        HomeItemManager.ATTENDANCE_MANAGEMENT ->
                            StartActivityManager.startAttendanceManagement(this)

                        HomeItemManager.GROUP_EDUCATION ->
                            StartActivityManager.startToTakePhotosOfDangers(
                                this,
                                FROM_GROUP_EDUCATION
                            )
                        HomeItemManager.VIDEO_SURVEILLANCE ->
                            StartActivityManager.startToVideoSurveillance(this)
                    }
                }
            }
        })

    }

    override fun observeViewModel() {
        viewModel.data.observe(this) {
            val items = HomeItemManager.getData()
            it?.let {
                data.add(
                    HomeHeaderBean(
                        projectName = "高淳至宣城高速公路江苏段工程项目",
                        bannerUrls = it
                    )
                )
            }?:let {
                data.add(
                    HomeHeaderBean(
                        projectName = "高淳至宣城高速公路江苏段工程项目",
                    )
                )
            }
            data.addAll(items)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun isRegisterEventbus(): Boolean {
        return true
    }

    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)
        MyLog.d("receiveEvent:$code")
        when (code) {
            EventCode.START_SCAN_QRCODE -> {
                reqCameraPermissionAndStart()
            }

            EventCode.START_CLOCK_IN_OUT -> {
                reqLocationPermissionAndStart()
            }
        }
    }

    private fun reqLocationPermissionAndStart() {
        (activity as? FragmentActivity)?.apply {
            PermissionManager.reqLocation(this) {
                StartActivityManager.startToClockInOut(this)
            }

        }
    }

    private fun reqCameraPermissionAndStart() {
        (activity as? FragmentActivity)?.apply {
            PermissionManager.requestCamera(this) {
                StartActivityManager.startToScanQrcode(this)
            }
        }
    }
}