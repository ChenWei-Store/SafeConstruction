package com.shuangning.safeconstruction.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
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
import com.shuangning.safeconstruction.bean.request.CommitRoutineInspectionReq
import com.shuangning.safeconstruction.bean.request.JianChaXiang
import com.shuangning.safeconstruction.bean.request.ZhengGaiChuLiRen
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.databinding.ActivityProblemReportBinding
import com.shuangning.safeconstruction.extension.prepareStartForResult
import com.shuangning.safeconstruction.manager.FROM_PROBLEM_REPORT
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.ProblemReportViewModel
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM_dd_HH_mm
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM_dd_HH_mm_ss
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM_dd_T_HH_mm_ss_SSS
import com.shuangning.safeconstruction.utils.ToastUtil
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/**
 * Created by Chenwei on 2023/10/16.
 */
class ProblemReportActivity : BaseActivity<ActivityProblemReportBinding>(),
    ActivityResultCallback<ActivityResult> {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addShowPhotoAdapter: AddShowPhotoMultiAdapter? = null
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private val maxLength = 500
    private var partOfTender: String = "" //标段
    private var inspectionClassification: String = "" //检查分类
    private var checkList: String = "" //检查项
    private var constructionTeam = "" //施工队
    private var selectedTime = "" //整改期限
    private var causeAnalysis = "" //原因分析
    private val maxPhotoNum = 9
    private val viewModel by viewModels<ProblemReportViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityProblemReportBinding? {
        return ActivityProblemReportBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("问题上报")
        addShowPhotoAdapter = AddShowPhotoMultiAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@ProblemReportActivity, 4)
            addItemDecoration(
                GridSpaceItemDecoration(
                    4, ScreenUtil.dp2px(16f),
                    ScreenUtil.dp2px(8f), false
                )
            )
            adapter = addShowPhotoAdapter
        }
        binding?.tvCount?.text = maxLength.toString()
        binding?.etContent?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        binding?.tvRectificationRequirementsCount?.text = maxLength.toString()
        binding?.etRectificationRequirements?.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        binding?.tvInspectionClassification?.text = inspectionClassification
        binding?.tvPartOfTender?.text = partOfTender
    }

    override fun initData() {
        data.add(AddPhoto())
        partOfTender = "GX-1标"
        inspectionClassification = "安全检查"
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewInspectionClassification?.setOnClickListener {
            //检查分类
            val data = arrayOf("安全检查", "质量检查")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data) { index, text ->
                inspectionClassification = text
                binding?.tvInspectionClassification?.text = inspectionClassification
            }
        }
        binding?.viewPartOfTender?.setOnClickListener {
            //标段
            val data = arrayOf("GX-1标", "GX-2标", "GX-21标")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data) { index, text ->
                partOfTender = text
                binding?.tvPartOfTender?.text = partOfTender
            }
        }
        binding?.viewConstructionTeam?.setOnClickListener {
            val data = arrayOf(
                "三场基建队",
                "三场钢筋大棚安拆队",
                "三场砌墙队",
                "三场临建队",
                "施工便道队",
                "施工便桥队",
                "桩基一队",
                "桩基二队",
                "路基一队",
                "路基二队",
                "下沟/小构1队",
                "下沟/小构2队",
                "钢筋加工场",
                "混凝土拌和站",
                "预制梁厂",
                "标准化建设"
            )
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data) { index, text ->
                constructionTeam = text
                binding?.tvConstructionTeam?.text = constructionTeam
            }
        }

        binding?.viewRectificationPeriod?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(
                this@ProblemReportActivity,
                TimePickerPopup.Mode.YMDHM,
                selectedCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedTime = TimeUtils.parseTime(date, yyyy_MM_dd_HH_mm_ss)
                            selectedCalendar.time = date
                            binding?.tvRectificationPeriod?.text = selectedTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.viewCheckList?.setOnClickListener {
            SelectCheckListActivity.startForResult(this@ProblemReportActivity, 1)
        }

        addShowPhotoAdapter?.setListener(object :
            AddShowPhotoMultiAdapter.OnPhotoActionClickListener {
            override fun onAdd() {
                PictureSelector.create(this@ProblemReportActivity)
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

        binding?.etContent?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvCount?.text = (maxLength - length).toString()
        }
        binding?.etRectificationRequirements?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvRectificationRequirements?.text = (maxLength - length).toString()
        }
        binding?.llCommit?.setOnClickListener {
            val desc = binding?.etContent?.text?.toString()
            if (desc.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请输入现场情况描述")
                return@setOnClickListener
            }
            if (inspectionClassification.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择检查分类")
                return@setOnClickListener
            }
            if (checkList.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择检查项")
                return@setOnClickListener
            }
            if (partOfTender.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择标段")
                return@setOnClickListener
            }
            if (constructionTeam.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择施工队")
                return@setOnClickListener
            }
            val rectificationRequirements = binding?.etRectificationRequirements?.text
            if (rectificationRequirements.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请输入整改要求")
                return@setOnClickListener
            }
            if (selectedTime.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择整改期限")
                return@setOnClickListener
            }
            if (data.isEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请上传图片")
                return@setOnClickListener
            }
            val paths = mutableListOf<String>()
            data.forEach {
                if (it is ShowPhoto) {
                    paths.add(it.url)
                }
            }
            LoadingManager.startLoading(this)
            viewModel.uploadPhotos(paths)
        }
    }

    override fun observeViewModel() {
        viewModel.photos.observe(this){
            val data = mutableListOf<UploadPhotoItem>()
            it?.let {
                data.addAll(it)
            }
            val jianchaxiang = JianChaXiang(2, "测试管理员")
            val chuliren = ZhengGaiChuLiRen(205)
            val desc = binding?.etContent?.text?.toString()?:""
            partOfTender = "HA4"
            constructionTeam="施工队"
            val rectificationRequirements = binding?.etRectificationRequirements?.text?.toString()?:""
            val req = CommitRoutineInspectionReq(0.0,0.0, inspectionClassification, data,
                jianchaxiang, partOfTender, constructionTeam, selectedTime, desc, rectificationRequirements, chuliren)
            viewModel.commit(req)
        }
        viewModel.uploadResult.observe(this){
            LoadingManager.stopLoading()
            if (it){
                ToastUtil.showCustomToast("提交成功")
                finish()
            }
        }

    }

    override fun onActivityResult(result: ActivityResult) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    onCheckListResult(data)
                }
            }
        }
    }

    private fun onCheckListResult(data: Intent?) {
        checkList = data?.getStringExtra("data") ?: ""
        binding?.tvCheckList?.text = checkList
    }
}