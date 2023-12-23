package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.AddPhoto
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.request.CommitRepairReq
import com.shuangning.safeconstruction.databinding.ActivityCommitRepairBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.CommitRepairViewModel
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.ActivityUtils
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/**
 * Created by Chenwei on 2023/12/23.
 */
class CommitRepairActivity: BaseActivity<ActivityCommitRepairBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addShowPhotoAdapter: AddShowPhotoMultiAdapter? = null
    private val maxPhotoNum = 9
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private var selectedNextCalendar: Calendar = Calendar.getInstance()
    private var selectedTime = ""
    private var selectedNextTime = ""
    private val viewModel by viewModels<CommitRepairViewModel>()
    private val id: Int by lazy {
        intent?.getIntExtra(ID, -1)?:-1
    }
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCommitRepairBinding? {
        return ActivityCommitRepairBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("添加维修")
        addShowPhotoAdapter = AddShowPhotoMultiAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@CommitRepairActivity, 4)
            addItemDecoration(
                GridSpaceItemDecoration(
                    4, ScreenUtil.dp2px(16f),
                    ScreenUtil.dp2px(8f), false
                )
            )
            adapter = addShowPhotoAdapter
        }
    }

    override fun initData() {
        data.add(AddPhoto())
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        addShowPhotoAdapter?.setListener(object :
            AddShowPhotoMultiAdapter.OnPhotoActionClickListener {
            override fun onAdd() {
                PictureSelector.create(this@CommitRepairActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setMaxSelectNum(maxPhotoNum)
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia> {
                        override fun onResult(result: ArrayList<LocalMedia>?) {
                            result.takeIf {
                                !it.isNullOrEmpty()
                            }?.let {
                                if (it.isNotEmpty()) {
                                    data.clear()
                                    it.forEach {
                                        val path = it.realPath
                                        val item = ShowPhoto(path, true)
                                        data.add(item)
                                    }
                                }
                                if (data.size < 9) {
                                    data.add(AddPhoto())
                                }
                                addShowPhotoAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancel() {
                        }

                    })
            }

            override fun onDelete(position: Int) {
            }

            override fun onImageClick() {
            }
        })
        binding?.view1?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(
                this@CommitRepairActivity,
                TimePickerPopup.Mode.YMD,
                selectedCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedTime = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                            selectedCalendar.time = date
                            binding?.tvTime?.text = selectedTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.view2?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(
                this@CommitRepairActivity,
                TimePickerPopup.Mode.YMD,
                selectedNextCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedNextTime = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                            selectedNextCalendar.time = date
                            binding?.tvTime2?.text = selectedNextTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.viewBottom?.setOnClickListener {
            val text = binding?.etContent?.text.toString()
            if (text.isNullOrEmpty()){
                ToastUtil.showCustomToast("请先输入维保内容")
                return@setOnClickListener
            }
            if (selectedTime.isEmpty()){
                ToastUtil.showCustomToast("请先选择维保时间")
                return@setOnClickListener
            }
            if (selectedNextTime.isEmpty()){
                ToastUtil.showCustomToast("请先选择下次维保时间")
                return@setOnClickListener
            }
            LoadingManager.startLoading(this)
            val req = CommitRepairReq(selectedTime, text, selectedNextTime, id)
            viewModel.commit(req)
        }
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            if (it){
                ToastUtil.showCustomToast("提交成功")
                finish()
            }else{
                ToastUtil.showCustomToast("提交失败")
            }
        }
    }

    companion object{
        const val ID = "id"
        fun startTo(ctx: Context, id: Int){
            ActivityUtils.start(ctx, CommitRepairActivity::class.java){
                putExtra(ID, id)
            }
        }
    }
}